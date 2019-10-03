package com.movies.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;
import com.movies.BuildConfig;
import com.movies.injection.ApplicationContext;

import java.io.File;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

import static com.movies.util.Constants.AppConstants.APP_NAME;

/**
 * Utilities for media player
 * source: https://github.com/google/ExoPlayer/blob/release-v2/demos/main/src/main/java/com/google/android/exoplayer2/demo/DemoApplication.java
 * */
@Singleton
public class MediaPlayerUtil {
    private static final String DOWNLOAD_CONTENT_DIRECTORY = "downloads";

    private Context mContext;
    private String mUserAgent;
    private DatabaseProvider mDatabaseProvider;
    private File mDownloadDirectory;
    private Cache mDownloadCache;
    private HashMap<String, Bitmap> mPreviewImages;

    @Inject
    public MediaPlayerUtil(@ApplicationContext Context context) {
        mContext = context;
        mPreviewImages = new HashMap<>();
        mUserAgent = Util.getUserAgent(context, APP_NAME);
    }

    /**
     * Returns a representative frame from the media file link. This method saves an instance
     * of the retrieved frame so that when accessing the same link more than once it won't need
     * to create another {@link MediaMetadataRetriever} to get the representative frame which is
     * time consuming but all the saved instance will be destroyed once the main activity is destroyed
     *
     * @param videoUrl The media file link
     * @return A {@link Single<Bitmap>} so that the task can be asynchronous
     * */
    public Single<Bitmap> getPreviewImage(String videoUrl) {
        return Single.create(emitter -> {
            Bitmap previewImage = mPreviewImages.get(videoUrl);

            // not null previewImage means that the videoUrl has already been paired with a Bitmap and just needs to emit that bitmap
            if (previewImage == null) {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(videoUrl, new HashMap<>());

                Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(-1);

                mPreviewImages.put(videoUrl, bitmap); // pair the bitmap with the url save it on the HashMap
                emitter.onSuccess(bitmap);
                return;
            }

            emitter.onSuccess(previewImage);
        });
    }


    /**
     * Build a MediaSource for media player
     * @param uri Uri of the media file
     * @return The created {@link ProgressiveMediaSource}
     * */
    public MediaSource createMediaSource(Uri uri) {
        return new ProgressiveMediaSource.Factory(buildDataSourceFactory())
                .createMediaSource(uri);
    }

    public RenderersFactory buildRenderersFactory(boolean preferExtensionRenderer) {
        @DefaultRenderersFactory.ExtensionRendererMode
        int extensionRendererMode =
                useExtensionRenderers()
                        ? (preferExtensionRenderer
                        ? DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
                        : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
                        : DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
        return new DefaultRenderersFactory(mContext)
                .setExtensionRendererMode(extensionRendererMode);
    }

    /** Returns a {@link DataSource.Factory}. */
    private DataSource.Factory buildDataSourceFactory() {
        DefaultDataSourceFactory upstreamFactory =
                new DefaultDataSourceFactory(mContext, buildHttpDataSourceFactory());
        return buildReadOnlyCacheDataSource(upstreamFactory, getDownloadCache());
    }

    /** Returns a {@link HttpDataSource.Factory}. */
    private HttpDataSource.Factory buildHttpDataSourceFactory() {
        return new DefaultHttpDataSourceFactory(mUserAgent);
    }

    /** Returns whether extension renderers should be used. */
    private boolean useExtensionRenderers() {
        //noinspection ConstantConditions
        return "withExtensions".equals(BuildConfig.FLAVOR);
    }

    private synchronized Cache getDownloadCache() {
        if (mDownloadCache == null) {
            File downloadContentDirectory = new File(getDownloadDirectory(), DOWNLOAD_CONTENT_DIRECTORY);
            mDownloadCache = new SimpleCache(downloadContentDirectory,
                    new NoOpCacheEvictor(),
                    getDatabaseProvider());
        }
        return mDownloadCache;
    }

    private DatabaseProvider getDatabaseProvider() {
        if (mDatabaseProvider == null) {
            mDatabaseProvider = new ExoDatabaseProvider(mContext);
        }
        return mDatabaseProvider;
    }

    private File getDownloadDirectory() {
        if (mDownloadDirectory == null) {
            mDownloadDirectory = mContext.getExternalFilesDir(null);
            if (mDownloadDirectory == null) {
                mDownloadDirectory = mContext.getFilesDir();
            }
        }
        return mDownloadDirectory;
    }

    private static CacheDataSourceFactory buildReadOnlyCacheDataSource(
            DataSource.Factory upstreamFactory, Cache cache) {
        return new CacheDataSourceFactory(cache,
                upstreamFactory,
                new FileDataSourceFactory(),
                null,
                CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
                null);
    }

}
