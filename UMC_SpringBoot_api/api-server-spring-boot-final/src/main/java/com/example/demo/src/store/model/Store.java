package com.example.demo.src.store.model;

import lombok.*;

@Getter // 해당 클래스에 대한 접근자 생성
@Setter // 해당 클래스에 대한 설정자 생성
@AllArgsConstructor

public class Store {
    int storeIdx;
    String typeName;
    String name;
    String address;
    String deliveryTip;
    Float grade;
    int dibs;
}
