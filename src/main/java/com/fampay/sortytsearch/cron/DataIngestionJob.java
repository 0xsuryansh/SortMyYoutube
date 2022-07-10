package com.fampay.sortytsearch.cron;

import com.fampay.sortytsearch.service.SearchResultIngestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.fampay.sortytsearch.constant.Constants.DelayConstants.FIXED_DELAY;

@Component
@EnableScheduling
public class DataIngestionJob {
    private SearchResultIngestionService ingestionService;
    @Autowired
    public DataIngestionJob(SearchResultIngestionService _searchResultIngestionService){
        ingestionService = _searchResultIngestionService;
    }

    /**
     * @Scheduled annotation can be used to configure and schedule tasks.
     * In this case, the duration between the end
     * of the last execution and the start of the next execution is fixed.
     * The task always waits until the previous one is finished.
     */
    @Scheduled(fixedDelay = FIXED_DELAY)
    public void dataPullSchedular(){
        ingestionService.asyncVideoUpdate();
    }
}
