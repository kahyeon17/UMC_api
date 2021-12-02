package com.example.demo.src.food;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.food.model.GetFoodMainRes;
import com.example.demo.src.food.model.GetFoodRes;
import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.src.store.StoreDao;
import com.example.demo.src.store.model.GetStoreRes;
import com.example.demo.src.store.model.PatchStoreReq;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service

public class FoodProvider {

    private final FoodDao foodDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FoodProvider(FoodDao foodDao, JwtService jwtService) {
        this.foodDao = foodDao;
        this.jwtService = jwtService;
    }


    // 해당 메뉴가 이미 Food 테이블에 존재하는지 확인
    public int checkMenu(String menu) throws BaseException {
        try {
            return foodDao.checkMenu(menu);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 12. storeName에 해당하는 메뉴 조회
    public List<GetFoodRes> getMenuByStore() throws BaseException {
        try {
            List<GetFoodRes> getFoodRes = foodDao.getMenuByStore();
            return getFoodRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetFoodRes> getMenuByStore(String storeName) throws BaseException {
        try {
            List<GetFoodRes> getFoodRes = foodDao.getMenuByStore(storeName);
            return getFoodRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 13. Store 의 대표메뉴 조회
    public List<GetFoodMainRes> getMainMenu() throws BaseException {
        try {
            List<GetFoodMainRes> getFoodMainRes = foodDao.getMainMenu();
            return getFoodMainRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
