package com.fampay.sortytsearch.client;

import com.fampay.sortytsearch.client.response.YoutubeSearchListResponse;

public interface YoutubeClient {
    YoutubeSearchListResponse fetchYoutubeSearchResults(String keywords);
}
