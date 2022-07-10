package com.fampay.sortytsearch.client.response;

import lombok.Data;

import java.util.ArrayList;

@Data
public class YoutubeSearchListResponse {
    private String kind;
    private String etag;
    private String nextPageToken;
    private String regionCode;
    private PageInfo pageInfo;
    private ArrayList<Item> items;
}
