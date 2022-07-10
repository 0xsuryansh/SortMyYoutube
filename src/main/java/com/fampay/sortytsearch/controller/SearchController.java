package com.fampay.sortytsearch.controller;

import com.fampay.sortytsearch.dbModel.Videos;
import com.fampay.sortytsearch.service.SearchResultIngestionService;
import com.fampay.sortytsearch.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchController {

    private final SearchResultIngestionService ingestionService;
    private final SearchService searchService;
    @Autowired
    public SearchController(SearchResultIngestionService _ingestionService,
                            SearchService _searchService){
        ingestionService = _ingestionService;
        searchService =_searchService;
    }

    @GetMapping("/video")
    public boolean getVideo(){
        ingestionService.asyncVideoUpdate();
        return true;
    }

    /**
     * Route/Controller
     * @param pageNo page number
     * @param pageSize page size
     * @return paginated response
     */
    @GetMapping("/fetchVideos")
    public List<Videos> getAllVideosWIthPaging(@RequestParam(defaultValue ="0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize){
        return searchService.getAllVideosPaginated(pageNo,pageSize);
    }

    /**
     * Returns videos matching search string on video title and description
     * @param q search string
     * @return list of videos matching the search string
     */
    @GetMapping("/search")
    public List<Videos> searchVideo(@RequestParam String q){
        return  searchService.search(q);
    }

}
