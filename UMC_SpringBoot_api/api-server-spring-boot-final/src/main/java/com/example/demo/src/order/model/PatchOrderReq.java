package com.example.demo.src.order.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class PatchOrderReq {
    private String food;
    private String userName;
}
