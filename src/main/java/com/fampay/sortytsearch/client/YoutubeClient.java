package com.fampay.sortytsearch.client;
import com.google.api.services.youtube.model.SearchListResponse;
public interface YoutubeClient {
    SearchListResponse fetchYoutubeSearchResults(String keywords);

}
