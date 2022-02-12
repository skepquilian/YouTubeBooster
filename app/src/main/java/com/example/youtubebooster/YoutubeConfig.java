package com.example.youtubebooster;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeThumbnailView;

public class YoutubeConfig extends YouTubeBaseActivity {
    private  String YOUTUBE_API_KEY = "AIzaSyCnlJ6YB3HC0JgcaXeN9JqCBt-xdWat0_A";

    public String getYOUTUBE_API_KEY() {
        return YOUTUBE_API_KEY;
    }

    public void setYOUTUBE_API_KEY(String YOUTUBE_API_KEY) {
        this.YOUTUBE_API_KEY = YOUTUBE_API_KEY;
    }

    public YoutubeConfig(String YOUTUBE_API_KEY) {
        this.YOUTUBE_API_KEY = YOUTUBE_API_KEY;
    }

    public YoutubeConfig() {
    }



}
