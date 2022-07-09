package com.fampay.sortytsearch.service.impl;

import com.fampay.sortytsearch.dbModel.Videos;
import com.fampay.sortytsearch.repository.VideoRepository;
import com.fampay.sortytsearch.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SearchImpl implements SearchService {

    VideoRepository repository;
    @Autowired
    public SearchImpl(VideoRepository _repository){
        repository = _repository;
    }

    @Override
    public List<Videos> search(String searchKey) {

        return null;
    }

    @Override
    public List<Videos> getAllVideosPaginated(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize,Sort.by("publishedDateTime").descending());
        Page<Videos> videos = repository.findAll(pageRequest);
        return videos.getContent();
    }
}