package com.example.cookle.pojo;

public class Id {
    private String videoId;
    private String kind;

    public Id(String videoId, String kind) {
        this.videoId = videoId;
        this.kind = kind;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
}
