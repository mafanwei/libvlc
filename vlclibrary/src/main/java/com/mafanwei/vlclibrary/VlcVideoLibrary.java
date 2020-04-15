package com.mafanwei.vlclibrary;

import org.videolan.libvlc.MediaPlayer;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;

import java.util.ArrayList;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;

public class VlcVideoLibrary implements MediaPlayer.EventListener {
    private int width = 0, height = 0;
    private LibVLC vlcInstance;
    private MediaPlayer player;
    private VlcListener vlcListener;
    //The library will select one of this class for rendering depend of constructor called
    private SurfaceView surfaceView;
    private TextureView textureView;
    private SurfaceTexture surfaceTexture;
    private Surface surface;
    private SurfaceHolder surfaceHolder;
    private ArrayList<String> options = new ArrayList<>();
    private boolean autoSize;
    private MediaPlayer.ScaleType scaleType;
    private IVLCVout vlcOut;

    private VlcVideoLibrary() {
    }

    public boolean isPlaying() {
        return player != null && player.isPlaying();
    }

    public void setPlayUrl(String endPoint) {
        if (player == null || player.isReleased()) {
            setMedia(new Media(vlcInstance, Uri.parse(endPoint)));
        }
    }

    public void setvlcVout() {
        setVout();
    }

    public void setPlayFile(String file) {
        if (player == null || player.isReleased()) {
            setMedia(new Media(vlcInstance, file));
        }
    }

    public void startPlay() {
        if(player !=null && player.hasMedia() && !player.isPlaying()) {
            player.play();
        }
    }

    public void stop() {
        if (player != null && player.isPlaying()) {
            player.stop();
        }
    }

    public void release() {
        if(player != null) {
            player.release();
        }
    }

    public void pause() {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    public void resumePlay() {
        if(player != null && player.hasMedia() && !player.isPlaying()) {
            setVout();
            player.play();
        }
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    private void setVout() {
        vlcOut = player.getVLCVout();
        //set correct class for render depend of constructor called
        if (surfaceView != null) {
            vlcOut.setVideoView(surfaceView);
            if (autoSize) {
                width = surfaceView.getWidth();
                height = surfaceView.getHeight();
            }
        } else if (textureView != null) {
            vlcOut.setVideoView(textureView);
            if (autoSize) {
                width = textureView.getWidth();
                height = textureView.getHeight();
            }
        } else if (surfaceTexture != null) {
            vlcOut.setVideoSurface(surfaceTexture);
        } else if (surface != null) {
            vlcOut.setVideoSurface(surface, surfaceHolder);
        } else {
            throw new RuntimeException("You cant set a null render object");
        }
        if (width != 0 && height != 0) vlcOut.setWindowSize(width, height);
        vlcOut.attachViews();
    }

    private void setMedia(Media media) {
        //delay = network buffer + file buffer
        //media.addOption(":network-caching=" + Constants.BUFFER);
        //media.addOption(":file-caching=" + Constants.BUFFER);
        media.setHWDecoderEnabled(true, false);
        player = new MediaPlayer(vlcInstance);
        for(String option : options) {
            media.addOption(option);
        }
        player.setMedia(media);
        player.setEventListener(this);
        player.setVideoScale(scaleType);
        player.setVideoTrackEnabled(true);
    }

    @Override
    public void onEvent(MediaPlayer.Event event) {
        switch (event.type) {
            case MediaPlayer.Event.Playing:
                vlcListener.onComplete();
                break;
            case MediaPlayer.Event.EncounteredError:
                vlcListener.onError();
                break;
            case MediaPlayer.Event.Buffering:
                vlcListener.onBuffering(event);
                break;
            default:
                break;
        }
    }

    public static class Builder {

        public VlcVideoLibrary vlcVideoLibrary;
        private Context context;

        public Builder(Context context) {
            this.context = context;
            vlcVideoLibrary = new VlcVideoLibrary();
        }

        public Builder setSurfaceView(SurfaceView surfaceView) {
            vlcVideoLibrary.surfaceView = surfaceView;
            return this;
        }

        public Builder setSurfaceTexture(SurfaceTexture surfaceTexture) {
            vlcVideoLibrary.surfaceTexture = surfaceTexture;
            return this;
        }

        public Builder setTextureView(TextureView textureView) {
            vlcVideoLibrary.textureView = textureView;
            return this;
        }

        public Builder setSurface(Surface surface) {
            vlcVideoLibrary.surface = surface;
            return this;
        }

        public Builder setWidth(int width) {
            vlcVideoLibrary.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            vlcVideoLibrary.height = height;
            return this;
        }

        public Builder setAutoSize(boolean autoSize) {
            vlcVideoLibrary.autoSize = autoSize;
            return this;
        }

        public Builder setVlcListener(VlcListener vlcListener) {
            vlcVideoLibrary.vlcListener = vlcListener;
            return this;
        }

        public Builder setScaleType(MediaPlayer.ScaleType scaleType) {
            vlcVideoLibrary.scaleType = scaleType;
            return this;
        }

        public Builder addOption(String option) {
            vlcVideoLibrary.options.add(option);
            return this;
        }

        public Builder setOptions(ArrayList<String> options) {
            vlcVideoLibrary.options = options;
            return this;
        }

        public Builder setSurfaceHolder(SurfaceHolder surfaceHolder) {
            vlcVideoLibrary.surfaceHolder = surfaceHolder;
            return this;
        }

        public VlcVideoLibrary create() {
            if (vlcVideoLibrary.vlcListener == null) {
                throw new NullPointerException("vlcListener shouldn't be null");
            }
            if(vlcVideoLibrary.vlcInstance == null) {
                    vlcVideoLibrary.vlcInstance = new LibVLC(context, new VlcOptions().getDefaultOptions());
            }
            context = null;
            return vlcVideoLibrary;
        }
    }
}
