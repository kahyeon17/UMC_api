package com.example.demo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class GetStoreRes {
    private int storeIdx;
    private String typeName;
    private String name;
    private String address;
    private String phoneNum;
    private Float grade;
    private int dibs;
}
