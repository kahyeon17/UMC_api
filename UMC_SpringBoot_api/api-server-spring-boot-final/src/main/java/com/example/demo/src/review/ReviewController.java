package com.example.demo.src.review;

import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.PostReviewRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/reviews")

public class ReviewController {

    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final ReviewProvider reviewProvider;
    @Autowired
    private final ReviewService reviewService;
    @Autowired
    private final JwtService jwtService;


    public ReviewController(ReviewProvider reviewProvider, ReviewService reviewService, JwtService jwtService) {
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }

    /**
     * 19. 해당 StoreName의 리뷰 조회 API
     * [GET] /reviews/? StoreName=
     */
    //Query String
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetReviewRes>> getReview(@RequestParam(required = false) String storeName) {
        try {
            if (storeName == null) {
                List<GetReviewRes> getReviewRes = reviewProvider.getReview();
                return new BaseResponse<>(getReviewRes);
            }
            // query string인 storeName이 있을 경우, 조건을 만족하는 메뉴들을 불러온다.
            List<GetReviewRes> getReviewRes = reviewProvider.getReview(storeName);
            return new BaseResponse<>(getReviewRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 20. 새로운 리뷰 작성 API
     * [POST] /reviews/add
     */
    //Body
    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<PostReviewRes> createReview(@RequestBody PostReviewReq postReviewReq) {
        if (postReviewReq.getGrade() == null) {
            return new BaseResponse<>(POST_REVIEWS_EMPTY_GRADE);
        }

        if (postReviewReq.getGrade() > 5.0) {
            return new BaseResponse<>(POST_REVIEWS_WRONG_GRADE);
        }

        try {
            PostReviewRes postReviewRes = reviewService.createReview(postReviewReq);
            return new BaseResponse<>(postReviewRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @Transactonal(rollbackFor = Exception.class)
    public void postReview(PostReviewReq postReviewReq) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM--dd");
        String orderTime = postReviewReq.getOrderAt();
        String current = format.format(System.currentTimeMillis());

        long calDateDays = 0;

        try {
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM--dd");
            Date orderDate = format2.parse(orderTime);
            Date currentDate = format2.parse(current);

            long calDate = currentDate.getTime() - orderDate.getTime();
            calDateDays = calDate / (24*60*60*1000);
            calDateDays = Math.abs(calDateDays);

            if (calDateDays > 5) {
                throw new Exception("리뷰 작성이 가능한 기간이 지났습니다.");
            }

        } catch (Exception e) {

        }

    }

}
