package com.example.demo.src.food;

import com.example.demo.src.food.model.GetFoodMainRes;
import com.example.demo.src.food.model.GetFoodRes;
import com.example.demo.src.food.model.PatchFoodReq;
import com.example.demo.src.food.model.PostFoodReq;
import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.src.store.model.GetStoreRes;
import com.example.demo.src.store.model.PatchStoreReq;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository //  [Persistence Layer에서 DAO를 명시하기 위해 사용]

public class FoodDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}


    // 12. 식당에 해당하는 메뉴 조회
    public List<GetFoodRes> getMenuByStore() {
        String getMenuByStoreQuery = "select * from Food order limit 2"; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getMenuByStoreQuery,
                (rs, rowNum) -> new GetFoodRes(
                        rs.getInt("foodIdx"),
                        rs.getString("menu"),
                        rs.getString("price"),
                        rs.getInt("mainStatus"))
        );
    }

    public List<GetFoodRes> getMenuByStore(String storeName) {
        String getMenuByStoreQuery = "select * from Food where storeName = ? order limit 2";
        String getMenuByStoreParams = storeName;
        return this.jdbcTemplate.query(getMenuByStoreQuery,
                (rs, rowNum) -> new GetFoodRes(
                        rs.getInt("foodIdx"),
                        rs.getString("menu"),
                        rs.getString("price"),
                        rs.getInt("mainStatus")),
                getMenuByStoreParams);
    }


    // 13. 식당들의 대표메뉴 조회
    public List<GetFoodMainRes> getMainMenu() {
        String getMenuByStoreQuery = "select * from Food where mainStatus = 1";
        return this.jdbcTemplate.query(getMenuByStoreQuery,
                (rs, rowNum) -> new GetFoodMainRes(
                        rs.getInt("foodIdx"),
                        rs.getString("storeName"),
                        rs.getString("menu"),
                        rs.getString("price"))
        );
    }

    // 14. 새로운 메뉴 등록
    public int createFood(PostFoodReq postFoodReq) {
        String createFoodQuery = "insert into Food (storeName, menu, price, mainStatus) VALUES (?,?,?,?)";
        Object[] createFoodParams = new Object[]{postFoodReq.getStoreName(), postFoodReq.getMenu(), postFoodReq.getPrice(), postFoodReq.getMainStatus()};
        this.jdbcTemplate.update(createFoodQuery, createFoodParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }

    // 메뉴 확인
    public int checkMenu(String menu) {
        String checkMenuQuery = "select exists(select menu from Food where menu = ?)"; // Food Table에 해당 menu를 갖는 음식 정보가 존재하는가?
        String checkMenuParams = menu; // 해당(확인할) 메뉴
        return this.jdbcTemplate.queryForObject(checkMenuQuery,
                int.class,
                checkMenuParams);
    }


    // 15. 음식 정보 수정 - 가격 ?menu=
    public int modifyFoodPrice(PatchFoodReq patchFoodReq) {
        String modifyFoodPriceQuery = "update Food set price = ? where menu = ? "; // 해당 menu를 만족하는 Food를 해당 price로 변경한다.
        Object[] modifyFoodPriceParams = new Object[]{patchFoodReq.getPrice(), patchFoodReq.getMenu()};

        return this.jdbcTemplate.update(modifyFoodPriceQuery, modifyFoodPriceParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }
}
