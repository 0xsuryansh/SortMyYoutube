package com.fampay.sortytsearch.dbModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("videos")
@Data
@AllArgsConstructor
public class Videos {
    @Id
    private String id;
    /**TextIndexed is used to make search
     * index for fuzzy search of mongo db atlas*/
    @TextIndexed
    private String videoTitle;

    @TextIndexed
    private String description;

    /**for fast paginated query*/
    @Indexed
    private Date publishedDateTime;

    private String thumbnailUrl;

    private String channelId;

    private String channelTitle;
}
