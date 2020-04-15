package com.mafanwei.vlcLibrary;

import java.util.ArrayList;

public class VlcOptions {

    public ArrayList<String> getDefaultOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add("-vvv");
        options.add(":fullscreen");
        return options;
    }
}
