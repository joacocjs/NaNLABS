package com.nanlabs.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardResponseDTO {
    private String id;
    private String name;
    private String desc;
    private String idList;
    private List<String> idLabels;
    private List<String> idMembers;
}
