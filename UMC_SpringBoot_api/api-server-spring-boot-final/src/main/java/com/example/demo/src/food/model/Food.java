package com.example.demo.src.food.model;

import lombok.*;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor

public class Food {
    private String storeName;
    private int foodIdx;
    private String menu;
    private String price;
}
