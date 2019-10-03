package com.movies.ui.video;

import android.content.Context;
import android.util.Pair;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer.DecoderInitializationException;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil.DecoderQueryException;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.movies.R;

/**
 * Provides readable error message for Exoplayer exceptions
 * */
public class PlayerErrorMessageProvider implements ErrorMessageProvider<ExoPlaybackException> {
    private Context mContext;

    PlayerErrorMessageProvider(Context context) {
        mContext = context;
    }

    /**
     * Returns a pair consisting of an error code and a user readable error message for the given
     * exception.
     * @param e The exception for which an error code and message should be generated.
     * @return A pair consisting of an error code and a user readable error message.
     * */
    @Override
    public Pair<Integer, String> getErrorMessage(ExoPlaybackException e) {
        String errorString = mContext.getString(R.string.error_generic);
        if (e.type == ExoPlaybackException.TYPE_RENDERER) {
            Exception cause = e.getRendererException();
            if (cause instanceof DecoderInitializationException) {
                // Special case for decoder initialization failures.
                DecoderInitializationException decoderInitializationException =
                        (DecoderInitializationException) cause;
                if (decoderInitializationException.decoderName == null) {
                    if (decoderInitializationException.getCause() instanceof DecoderQueryException) {
                        errorString = mContext.getString(R.string.error_querying_decoders);
                    } else if (decoderInitializationException.secureDecoderRequired) {
                        errorString = mContext.getString(R.string.error_no_secure_decoder, decoderInitializationException.mimeType);
                    } else {
                        errorString = mContext.getString(R.string.error_no_decoder, decoderInitializationException.mimeType);
                    }
                } else {
                    errorString = mContext.getString(R.string.error_instantiating_decoder, decoderInitializationException.decoderName);
                }
            }
        }
        return Pair.create(0, errorString);
    }
}
