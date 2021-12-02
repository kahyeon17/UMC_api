package com.example.demo.src.store.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PatchStoreReq {
    private String name;
    private int dibs;
}
