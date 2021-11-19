package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.order.model.PatchOrderReq;
import com.example.demo.src.order.model.PostOrderReq;
import com.example.demo.src.order.model.PostOrderRes;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import static com.example.demo.config.BaseResponseStatus.*;

@Service

public class OrderService {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final OrderDao orderDao;
    private final OrderProvider orderProvider;
    private final JwtService jwtService;


    @Autowired //readme 참고
    public OrderService(OrderDao orderDao, OrderProvider orderProvider, JwtService jwtService) {
        this.orderDao = orderDao;
        this.orderProvider = orderProvider;
        this.jwtService = jwtService;

    }

    // 17. 새로은 주문 등록
    public PostOrderRes createOrder(PostOrderReq postOrderReq) throws BaseException {
        try{
            throw new Exception();
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 발생
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 18. 주문 정보 수정
    public void modifyOrderFood(PatchOrderReq patchOrderReq) throws BaseException {
        try {
            int result = orderDao.modifyOrderFood(patchOrderReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메시지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_FOOD);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
