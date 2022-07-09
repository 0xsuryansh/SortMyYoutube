package com.fampay.sortytsearch.dbModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("videos")
@Data
@AllArgsConstructor

public class Videos {
    @Id
    private String id;

    @Indexed(name = "videoTitleIndex")
    private String videoTitle;

    @Indexed(name = "descriptionIndex")
    private String description;

    private Date publishedDateTime;

    private String thumbnailUrl;

    private String channelId;

    private String channelTitle;
}
