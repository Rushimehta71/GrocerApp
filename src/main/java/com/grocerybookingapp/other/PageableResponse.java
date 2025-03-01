package com.grocerybookingapp.other;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageableResponse<T> {
private List<T> content;
private int pageNumebr;
private int pageSize;
private long totalElements;
private int totalPages;
private boolean lastPage;
}
