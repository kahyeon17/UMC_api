package com.example.demo.src.food.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor


public class GetFoodRes {
    private int foodIdx;
    private String menu;
    private String price;
    private int mainStatus;
}
