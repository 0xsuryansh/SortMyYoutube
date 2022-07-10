package com.fampay.sortytsearch.service.impl;

import com.fampay.sortytsearch.dbModel.Videos;
import com.fampay.sortytsearch.repository.VideoRepository;
import com.fampay.sortytsearch.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SearchImpl implements SearchService {

    VideoRepository repository;
    @Autowired
    public SearchImpl(VideoRepository _repository){
        repository = _repository;
    }

    /**
     * fuzzy search on video title and description fields
     * for a given search string
     * @param searchKey search string to return results for
     * @return
     */
    @Override
    public List<Videos> search(String searchKey) {
        TextCriteria search = TextCriteria.forDefaultLanguage().matching(searchKey);
        return repository.findAllBy(search);
    }

    /**
     * Returns the stored video data in a paginated
     * response sorted in descending order of published datetime.
     * @param pageNo
     * @param pageSize
     * @return paginated response in chronologically descending order of publishing
     */
    @Override
    public List<Videos> getAllVideosPaginated(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize,Sort.by("publishedDateTime").descending());
        Page<Videos> videos = repository.findAll(pageRequest);
        return videos.getContent();
    }
}
