package com.example.demo.src.food.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor

public class GetFoodMainRes {
    private int storeIdx;
    private String storeName;
    private String menu;
    private String price;
}
