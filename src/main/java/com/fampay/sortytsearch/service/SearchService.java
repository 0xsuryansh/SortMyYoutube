package com.fampay.sortytsearch.service;

import com.fampay.sortytsearch.dbModel.Videos;

import java.util.List;

public interface SearchService {
    List<Videos> search(String searchKey);
    List<Videos> getAllVideosPaginated(int pageNo, int pageSize);
}
