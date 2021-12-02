package com.example.demo.src.order.model;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PostOrderReq {
    private String userName;
    private String storeName;
    private String getMethod;
    private String food;
}
