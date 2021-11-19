package com.example.demo.src.order;

import com.example.demo.src.food.FoodProvider;
import com.example.demo.src.food.FoodService;
import com.example.demo.src.food.model.GetFoodRes;
import com.example.demo.src.order.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/orders")

public class OrderController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OrderProvider orderProvider;
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final JwtService jwtService;


    public OrderController(OrderProvider orderProvider, OrderService orderService, JwtService jwtService) {
        this.orderProvider = orderProvider;
        this.orderService = orderService;
        this.jwtService = jwtService;
    }


    /**
     * 16. 주문 by User 조회 API
     * [GET] /orders? UserName=
     */
    //Query String
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetOrderRes>> getOrderByUser(@RequestParam(required = false) String userName) {
        try {
            if (userName == null) {
                List<GetOrderRes> getOrderRes = orderProvider.getOrder();
                return new BaseResponse<>(getOrderRes);
            }
            // query string인 userName이 있을 경우, 조건을 만족하는 메뉴들을 불러온다.
            List<GetOrderRes> getOrderRes = orderProvider.getOrderByUser(userName);
            return new BaseResponse<>(getOrderRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 17. 새로운 주문 등록 API
     * [POST] /orders/add
     */
    //Body
    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<PostOrderRes> createOrder(@RequestBody PostOrderReq postOrderReq) {
        if (postOrderReq.getUserName() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }

        if (!isRegexEmail(postOrderReq.getUserName())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try {
            PostOrderRes postOrderRes = orderService.createOrder(postOrderReq);
            return new BaseResponse<>(postOrderRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 18. 주문 정보 (food) 수정 API
     * [PATCH] /orders/? UserName=
     */
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> modifyOrderFood(@RequestParam(required = false) String userName, @RequestBody Order order) {
        try {
            //jwt에서idx추출.
//            String nameByJwt = jwtService.getName();
            //name과 접근한 유저가 같은지 확인
//            if(name != nameByJwt){
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }

            PatchOrderReq patchOrderReq = new PatchOrderReq(userName, order.getFood());
            orderService.modifyOrderFood(patchOrderReq);

            String result = "주문한 음식이 변경되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
