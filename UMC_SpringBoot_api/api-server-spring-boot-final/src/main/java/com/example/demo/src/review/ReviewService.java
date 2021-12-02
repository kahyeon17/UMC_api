package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.src.food.FoodDao;
import com.example.demo.src.food.FoodProvider;
import com.example.demo.src.food.model.PatchFoodReq;
import com.example.demo.src.food.model.PostFoodReq;
import com.example.demo.src.food.model.PostFoodRes;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.PostReviewRes;
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

public class ReviewService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final ReviewDao reviewDao;
    private final ReviewProvider reviewProvider;
    private final JwtService jwtService;


    @Autowired //readme 참고
    public ReviewService(ReviewDao reviewDao, ReviewProvider reviewProvider, JwtService jwtService) {
        this.reviewDao = reviewDao;
        this.reviewProvider = reviewProvider;
        this.jwtService = jwtService;

    }

    // 20. 새로운 리뷰 작성
    public PostReviewRes createReview(PostReviewReq postReviewReq) throws BaseException {
        try {
            int reviewIdx = reviewDao.createReview(postReviewReq);
            return new PostReviewRes(reviewIdx);

//            //jwt 발급.
//            String jwt = jwtService.createJwt(userIdx);
//            return new PostUserRes(jwt,userIdx);

        }  catch (Exception exception) { // DB에 이상이 있는 경우 에러 발생
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
