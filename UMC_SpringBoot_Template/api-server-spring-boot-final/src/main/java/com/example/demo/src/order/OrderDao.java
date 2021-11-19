package com.example.demo.src.order;

import com.example.demo.src.food.model.GetFoodRes;
import com.example.demo.src.food.model.PatchFoodReq;
import com.example.demo.src.food.model.PostFoodReq;
import com.example.demo.src.order.model.GetOrderRes;
import com.example.demo.src.order.model.PatchOrderReq;
import com.example.demo.src.order.model.PostOrderReq;
import com.example.demo.src.store.model.GetStoreRes;
import com.example.demo.src.store.model.PatchStoreReq;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository //  [Persistence Layer에서 DAO를 명시하기 위해 사용]

public class OrderDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

    // 16. 주문 조회
    public List<GetOrderRes> getOrder() {
        String getOrderQuery = "select * from Order"; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getOrderQuery,
                (rs, rowNum) -> new GetOrderRes(
                        rs.getInt("orderIdx"),
                        rs.getString("userName"),
                        rs.getString("storeName"),
                        rs.getString("getMethod"),
                        rs.getString("food"))
        );
    }

    public List<GetOrderRes> getOrderByUser(String userName) {
        String getOrderByUserQuery = "select * from Order userName = ?";
        String getOrderByUSerParams = userName;
        return this.jdbcTemplate.query(getOrderByUserQuery,
                (rs, rowNum) -> new GetOrderRes(
                        rs.getInt("orderIdx"),
                        rs.getString("userName"),
                        rs.getString("storeName"),
                        rs.getString("getMethod"),
                        rs.getString("food")),
                getOrderByUSerParams);
    }


    // 17. 새로운 주문 등록
    public int createOrder(PostOrderReq postOrderReq) {
        String createOrderQuery = "insert into Order (userName, storeName, getMethod, food) VALUES (?,?,?,?)";
        Object[] createOrderParams = new Object[]{postOrderReq.getUserName(), postOrderReq.getStoreName(), postOrderReq.getGetMethod(), postOrderReq.getFood()};
        this.jdbcTemplate.update(createOrderQuery, createOrderParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }


    // 18. 주문 수정 ?userName=
    public int modifyOrderFood(PatchOrderReq patchOrderReq) {
        String modifyOrderFoodQuery = "update User set food = ? where userName = ? "; // 해당 userName을 만족하는 Order를 해당 food로 변경한다.
        Object[] modifyOrderFoodParams = new Object[]{patchOrderReq.getFood(), patchOrderReq.getUserName()};

        return this.jdbcTemplate.update(modifyOrderFoodQuery, modifyOrderFoodParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

}
