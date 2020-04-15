package com.mafanwei.vlcLibrary;

import org.videolan.libvlc.MediaPlayer;

public interface VlcListener {

    void OnVlcOccur(MediaPlayer.Event event);
}
