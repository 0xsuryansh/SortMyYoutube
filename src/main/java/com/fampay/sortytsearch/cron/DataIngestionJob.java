package com.fampay.sortytsearch.cron;

import com.fampay.sortytsearch.service.SearchResultIngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.fampay.sortytsearch.constant.Constants.DelayConstants.FIXED_DELAY;

@Component
public class DataIngestionJob {
    private final SearchResultIngestionService ingestionService;
    @Autowired
    public DataIngestionJob(SearchResultIngestionService _searchResultIngestionService){
        ingestionService = _searchResultIngestionService;
    }

    @Scheduled(fixedDelay = FIXED_DELAY)
    public void dataPullSchedular(){
        ingestionService.asyncVideoUpdate();
    }
}
