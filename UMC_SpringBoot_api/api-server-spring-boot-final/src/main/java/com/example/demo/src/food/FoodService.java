package com.example.demo.src.food;


import com.example.demo.config.BaseException;
import com.example.demo.src.food.model.PatchFoodReq;
import com.example.demo.src.food.model.PostFoodReq;
import com.example.demo.src.food.model.PostFoodRes;
import com.example.demo.src.store.StoreDao;
import com.example.demo.src.store.StoreProvider;
import com.example.demo.src.store.model.PatchStoreReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service

public class FoodService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final FoodDao foodDao;
    private final FoodProvider foodProvider;
    private final JwtService jwtService;


    @Autowired //readme 참고
    public FoodService(FoodDao foodDao, FoodProvider foodProvider, JwtService jwtService) {
        this.foodDao = foodDao;
        this.foodProvider = foodProvider;
        this.jwtService = jwtService;

    }

    // 14. 새로운 메뉴 등록
    public PostFoodRes createMenu(PostFoodReq postFoodReq) throws BaseException {
        // 중복 확인
        if (foodProvider.checkMenu(postFoodReq.getMenu()) == 1) {
            throw new BaseException(POST_FOODS_EXISTS_MENU);
        }
        try {
            int foodIdx = foodDao.createFood(postFoodReq);
            return new PostFoodRes(foodIdx);

//            //jwt 발급.
//            String jwt = jwtService.createJwt(userIdx);
//            return new PostUserRes(jwt,userIdx);

        }  catch (Exception exception) { // DB에 이상이 있는 경우 에러 발생
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 15. 음식 정보 수정 - 가격
    public void modifyFoodPrice(PatchFoodReq patchFoodReq) throws BaseException {
        try {
            int result = foodDao.modifyFoodPrice(patchFoodReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메시지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_FOODPRICE);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
