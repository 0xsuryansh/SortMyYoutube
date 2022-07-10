package com.fampay.sortytsearch.client.response;

import lombok.Data;

import java.util.Date;

@Data
public class Snippet{
    private Date publishedAt;
    private String channelId;
    private String title;
    private String description;
    private Thumbnails thumbnails;
    private String channelTitle;
    private String liveBroadcastContent;
    private Date publishTime;
}
