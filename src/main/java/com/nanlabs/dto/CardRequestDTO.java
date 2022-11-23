package com.nanlabs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@ApiModel(value = "Card")
public class CardRequestDTO {
    private String type;
    private String title;
    private String description;
    private String category;
}
