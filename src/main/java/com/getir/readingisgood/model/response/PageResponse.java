package com.getir.readingisgood.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse<T> {
    @JsonProperty("content")
    private List<T> contentList;
    private Integer max;
    private Integer next;
    private Long total;
}
