package com.mafanwei.libvlc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import com.mafanwei.vlclibrary.VlcListener;
import com.mafanwei.vlclibrary.VlcVideoLibrary;

import org.videolan.libvlc.MediaPlayer;

public class MainActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private VlcVideoLibrary vlcVideoLibrary;
    private boolean first = true;
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView = findViewById(R.id.sv);
        VlcVideoLibrary.Builder builder = new VlcVideoLibrary.Builder(this);
        builder.setSurfaceView(surfaceView).setVlcListener(new VlcListener() {
            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }

            @Override
            public void onError() {
                Log.e(TAG, "onError");
            }

            @Override
            public void onBuffering(org.videolan.libvlc.MediaPlayer.Event event) {
                Log.d(TAG, "onBuffering");
            }
        });
       // builder.addOption("--vout=android-display");
        //builder.setScaleType(MediaPlayer.ScaleType.SURFACE_FIT_SCREEN);
          builder.setHeight(1920);
          builder.setWidth(1080);
        //builder.setHeight(3024);
          builder.setAutoSize(false);
          builder.addOption(":network-caching=0");
          builder.addOption(":file-caching=0");
        //  builder.addOption("--rotate-angle=180");
        // builder.addOption("--gradient-mode=edge");
        vlcVideoLibrary = builder.create();
       // vlcVideoLibrary.setPlayUrl("rtsp://192.168.31.168/0");
        //vlcVideoLibrary.playFile(Environment.getExternalStorageDirectory()+"/mfw.mp4");
        vlcVideoLibrary.setPlayUrl("rtsp://192.168.31.192:1234");
        vlcVideoLibrary.getPlayer().setVideoTrackEnabled(false);
        //vlcVideoLibrary.getPlayer().setAudioDelay(150000);
        vlcVideoLibrary.setvlcVout();
        // vlcVideoLibrary.setPlayUrl("file://"+Environment.getExternalStorageDirectory()+"/mfw.mp4");
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
