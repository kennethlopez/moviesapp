package com.movies.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.RenderersFactory;
import com.google.android.exoplayer2.database.DatabaseProvider;
import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.offline.ActionFileUpgradeUtil;
import com.google.android.exoplayer2.offline.DefaultDownloadIndex;
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
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.movies.BuildConfig;
import com.movies.injection.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

import static com.movies.util.Constants.AppConstants.APP_NAME;

@Singleton
public class MediaPlayerUtil {
    private static final String TAG = "MediaPlayerUtil";
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

    /** Returns a {@link DataSource.Factory}. */
    public DataSource.Factory buildDataSourceFactory() {
        DefaultDataSourceFactory upstreamFactory =
                new DefaultDataSourceFactory(mContext, buildHttpDataSourceFactory());
        return buildReadOnlyCacheDataSource(upstreamFactory, getDownloadCache());
    }

    public MediaSource createMediaSource(Uri uri) {
        return new ProgressiveMediaSource.Factory(buildDataSourceFactory())
                .createMediaSource(uri);
    }

    /** Returns a {@link HttpDataSource.Factory}. */
    public HttpDataSource.Factory buildHttpDataSourceFactory() {
        return new DefaultHttpDataSourceFactory(mUserAgent);
    }

    /** Returns whether extension renderers should be used. */
    public boolean useExtensionRenderers() {
        return "withExtensions".equals(BuildConfig.FLAVOR);
    }

    public Single<Bitmap> getPreviewImage(String videoUrl) {
        return Single.create(emitter -> {
            Bitmap previewImage = mPreviewImages.get(videoUrl);
            if (previewImage == null) {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(videoUrl, new HashMap<>());

                Bitmap bitmap = mediaMetadataRetriever.getFrameAtTime(-1);

                mPreviewImages.put(videoUrl, bitmap);
                emitter.onSuccess(bitmap);
            }

            emitter.onSuccess(previewImage);
        });
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

    protected synchronized Cache getDownloadCache() {
        if (mDownloadCache == null) {
            File downloadContentDirectory = new File(getDownloadDirectory(), DOWNLOAD_CONTENT_DIRECTORY);
            mDownloadCache = new SimpleCache(downloadContentDirectory,
                    new NoOpCacheEvictor(),
                    getDatabaseProvider());
        }
        return mDownloadCache;
    }

    private void upgradeActionFile(
            String fileName, DefaultDownloadIndex downloadIndex, boolean addNewDownloadsAsCompleted) {
        try {
            ActionFileUpgradeUtil.upgradeAndDelete(
                    new File(getDownloadDirectory(), fileName),
                    null,
                    downloadIndex,
                    true,
                    addNewDownloadsAsCompleted);
        } catch (IOException e) {
            Log.e(TAG, "Failed to upgrade action file: " + fileName, e);
        }
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

    protected static CacheDataSourceFactory buildReadOnlyCacheDataSource(
            DataSource.Factory upstreamFactory, Cache cache) {
        return new CacheDataSourceFactory(cache,
                upstreamFactory,
                new FileDataSourceFactory(),
                null,
                CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR,
                null);
    }

}
