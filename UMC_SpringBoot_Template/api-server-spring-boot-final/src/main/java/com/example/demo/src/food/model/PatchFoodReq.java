package com.example.demo.src.food.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PatchFoodReq {
    private String menu;
    private String price;
}
