package com.fampay.sortytsearch.service.impl;

import com.fampay.sortytsearch.client.YoutubeClient;
import com.fampay.sortytsearch.client.response.Item;
import com.fampay.sortytsearch.client.response.YoutubeSearchListResponse;
import com.fampay.sortytsearch.dbModel.Videos;
import com.fampay.sortytsearch.repository.VideoRepository;
import com.fampay.sortytsearch.service.SearchResultIngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class IngestionImpl implements SearchResultIngestionService {
    private final YoutubeClient client;
    private final VideoRepository videoRepository;
    private static String SEARCH_QUERY;
    @Autowired
    public IngestionImpl(YoutubeClient _client,
                         VideoRepository _repository,
                         @Value("${searchQuery}") String query){
        client = _client;
        videoRepository = _repository;
        SEARCH_QUERY = query;
    }

    /**
     * YouTube API is continuously called in background (async)
     * with some interval (say 10 seconds) for fetching the latest videos for a
     * predefined search query and stores the data of videos
     * in a database.
     */
    @Override
    public void asyncVideoUpdate() {
        YoutubeSearchListResponse response = client.fetchYoutubeSearchResults(SEARCH_QUERY);
        if(Objects.isNull(response) || Objects.isNull(response.getItems())){
            return;
        }
        List<Videos> videos = new ArrayList<>();
        for (Item item:
             response.getItems()) {
            String id = item.getId().getVideoId();
            String videoTitle = item.getSnippet().getTitle();
            String description = item.getSnippet().getDescription();
            Date publishedDate = item.getSnippet().getPublishedAt();
            String url = item.getSnippet().getThumbnails().getMydefault().getUrl();
            String channelId = item.getSnippet().getChannelId();
            String channelTitle = item.getSnippet().getChannelTitle();
            videos.add(new Videos(id,videoTitle,description,publishedDate,url,channelId,channelTitle));
        }
        videoRepository.saveAll(videos);
    }
}
