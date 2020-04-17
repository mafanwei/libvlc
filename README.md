# libvlc
vlc for android

The package of libvlc makes it easier to use

Lastest：[![](https://jitpack.io/v/mafanwei/libvlc.svg)](https://jitpack.io/#mafanwei/libvlc)
Build with libvlc：3.2.5

## Quick Start
[中文介绍](https://blog.csdn.net/qwe25878/article/details/105579760)

**1.** Add library to your project:

Add jitpack.io repository to your root build.gradle:
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
Add library to dependencies
```gradle
dependencies {
    implementation 'com.github.mafanwei:libvlc:Tag'
}
```

## Usage
#### Basic usage.
Use ``` VlcVideoLibrary.Builder``` to build VlcVideoLibrary.
And use ```VlcVideoLibrary``` to play.

```
VlcVideoLibrary.Builder builder = new VlcVideoLibrary.Builder(this, true);
//set a render object: Surface/SurfaceView/TextureView/SurfaceTexture
builder.setSurfaceView(surfaceView);
//create VlcVideoLibrary.
VlcVideoLibrary vlcVideoLibrary = builder.create();
//set play url, such as a rtsp.
vlcVideoLibrary.setPlayUrl("rtsp://192.168.31.192:1234");
//set output.
vlcVideoLibrary.setVlcVout();
//now start play:
vlcVideoLibrary.startPlay();
```

#### VlcVideoLibrary Builder
``` VlcVideoLibrary.Builder``` has some apis to help you.

|name|description|
|-|-|
|setWidth|set the pixel width of the video|
|setHeight|set the pixel height of the video|
|setScaleType|set the video scale type, by default, scaletype is set to ScaleType.SURFACE_BEST_FIT|
|setSurface|set a render Surface|
|setSurfaceView|set a render SurfaceView|
|setTextureView|set a render TextureView|
|setSurfaceTexture|set a render surfaceTexture|
|useFullScreen|make video full screen|
|useNoCache|make the video not use cache|
|useRtspTcp|it can make rtsp playback more stable, but not all streaming devices support it|
|addOption|if you know the parameters of libvlc, you can use it add some options you want|
|setOptions|set libvlc options|
|removeOption|remove some options you want|
|clearOptions|clear all options|
|setAutoSize|if true make video auto size|
|setVlcListener|set a listener to notice what happened on libvlc|

For more apis, please download code.
#### Donate

Buy a cup of coffee for the author.

<img src="https://img-blog.csdnimg.cn/20181205161540134.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3F3ZTI1ODc4,size_16,color_FFFFFF,t_70" width="375" alt="alipay"/>
<img src="https://img-blog.csdnimg.cn/20181205162201519.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3F3ZTI1ODc4,size_16,color_FFFFFF,t_70" width="375" alt="wechat"/>

#### Other
If you have good ideas, please discuss with me.
Pull request is always welcome.
