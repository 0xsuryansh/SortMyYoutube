package com.fampay.sortytsearch.service.impl;

import com.fampay.sortytsearch.client.YoutubeClient;
import com.fampay.sortytsearch.client.response.Item;
import com.fampay.sortytsearch.client.response.YoutubeSearchListResponse;
import com.fampay.sortytsearch.dbModel.Videos;
import com.fampay.sortytsearch.repository.VideoRepository;
import com.fampay.sortytsearch.service.SearchResultIngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fampay.sortytsearch.constant.Constants.DelayConstants.FIXED_DELAY;

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
        if(response==null){
            return;
        }
        List<Videos> videos = new ArrayList<>();
        for (Item item:
             response.items) {
            String id = item.id.videoId;
            String videoTitle = item.snippet.title;
            String description = item.snippet.description;
            Date publishedDate = item.snippet.publishedAt;
            String url = item.snippet.thumbnails.mydefault.url;
            String channelId = item.snippet.channelId;
            String channelTitle = item.snippet.channelTitle;
            videos.add(new Videos(id,videoTitle,description,publishedDate,url,channelId,channelTitle));
        }
        videoRepository.saveAll(videos);
    }
}
