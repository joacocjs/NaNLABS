package com.nanlabs.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ListResponseDTO {

    private String id;
    private String name;
    private String idBoard;
    //private String closed;
    //private String pos;
    //private String subscribed;
    //private String softLimit;
}

