package com.example.demo.src.food;

import com.example.demo.src.food.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/foods")

public class FoodController {

    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final FoodProvider foodProvider;
    @Autowired
    private final FoodService foodService;
    @Autowired
    private final JwtService jwtService;


    public FoodController(FoodProvider foodProvider, FoodService foodService, JwtService jwtService) {
        this.foodProvider = foodProvider;
        this.foodService = foodService;
        this.jwtService = jwtService;
    }


    /**
     * 12. 해당 StoreName의 메뉴 조회 API
     * [GET] /foods/? StoreName=
     */
    //Query String
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetFoodRes>> getMenuByStore(@RequestParam(required = false) String storeName) {
        try {
            if (storeName == null) {
                List<GetFoodRes> getFoodRes = foodProvider.getMenuByStore();
                return new BaseResponse<>(getFoodRes);
            }
            // query string인 storeName이 있을 경우, 조건을 만족하는 메뉴들을 불러온다.
            List<GetFoodRes> getFoodRes = foodProvider.getMenuByStore(storeName);
            return new BaseResponse<>(getFoodRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 13. Store의 대표메뉴 조회 API
     * [GET] /foods/main
     */
    //Query String
    @ResponseBody
    @GetMapping("/main")
    public BaseResponse<List<GetFoodMainRes>> getMainMenu(@RequestParam(required = false) int foodIdx) {
        try {
            List<GetFoodMainRes> getFoodMainRes = foodProvider.getMainMenu();
            return new BaseResponse<>(getFoodMainRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 14. 새로운 메뉴 등록 API
     * [POST] /foods/add
     */
    //Body
    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<PostFoodRes> createMenu(@RequestBody PostFoodReq postFoodReq) {
        if (postFoodReq.getMenu() == null) {
            return new BaseResponse<>(POST_FOODS_EMPTY_MENU);
        }

        try {
            PostFoodRes postFoodRes = foodService.createMenu(postFoodReq);
            return new BaseResponse<>(postFoodRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 15. 음식 정보 (price) 수정 API
     * [PATCH] /foods/? Menu=
     */
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> modifyFoodPrice(@RequestParam(required = false) String menu, @RequestBody Food food) {
        try{
            //jwt에서menu추출.
//            int menuByJwt = jwtService.getMenu();
            //menu와 접근한 음식이 같은지 확인
//            if(menu != menuByJwt){
//                return new BaseResponse<>(INVALID_MENU_JWT);
//            }

            PatchFoodReq patchFoodReq = new PatchFoodReq(menu, food.getPrice());
            foodService.modifyFoodPrice(patchFoodReq);

            String result = "메뉴 가격이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
