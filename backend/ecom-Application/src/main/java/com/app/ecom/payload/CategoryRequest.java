package com.app.ecom.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor


//CategoryDTO
public class CategoryRequest {
    private Long categoryId;
    private String categoryName;
}
