package com.getir.readingisgood.model.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    @NotNull
    private List<BookDetailRequest> bookDetailRequestList;
    private String orderName;
}
