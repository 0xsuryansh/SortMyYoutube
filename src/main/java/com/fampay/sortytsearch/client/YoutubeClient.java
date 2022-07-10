package com.fampay.sortytsearch.client;

import com.fampay.sortytsearch.client.response.YoutubeSearchListResponse;

public interface YoutubeClient {
    /**
     *
     * @param keywords
     * @return
     */
    YoutubeSearchListResponse fetchYoutubeSearchResults(String keywords);
}
