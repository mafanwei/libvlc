package com.mafanwei.libvlc;


import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;

import com.mafanwei.vlcLibrary.VlcVideoLibrary;

import org.videolan.libvlc.MediaPlayer;


public class MainActivity extends Activity {

    private SurfaceView surfaceView;
    private VlcVideoLibrary vlcVideoLibrary;
    private boolean first = true;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.sv);
        VlcVideoLibrary.Builder builder = new VlcVideoLibrary.Builder(this, true);
        builder.setSurfaceView(surfaceView);
        builder.setScaleType(MediaPlayer.ScaleType.SURFACE_FIT_SCREEN);
        builder.useNoCache();
        builder.useFullScreen();
        vlcVideoLibrary = builder.create();
        vlcVideoLibrary.setPlayUrl("rtsp://192.168.31.168/0");
        // vlcVideoLibrary.setPlayUrl("file://"+Environment.getExternalStorageDirectory()+"/mfw.mp4");
        //vlcVideoLibrary.getPlayer().setVideoTrackEnabled(false);
        vlcVideoLibrary.setVlcVout();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vlcVideoLibrary.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!first) {
            vlcVideoLibrary.resumePlay();
        } else {
            vlcVideoLibrary.startPlay();
            first = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vlcVideoLibrary.stop();
        vlcVideoLibrary.release();
    }
}
