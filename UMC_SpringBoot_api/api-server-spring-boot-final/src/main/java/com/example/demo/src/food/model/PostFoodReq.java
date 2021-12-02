package com.example.demo.src.food.model;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PostFoodReq {
    private String storeName;
    private String menu;
    private String price;
    private int mainStatus;
}
