package com.example.demo.src.review;

import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.src.review.model.PostReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository //  [Persistence Layer에서 DAO를 명시하기 위해 사용]

public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}

    // 19. 리뷰 조회 - 별점 높은 순
    public List<GetReviewRes> getReview() {
        String getReviewQuery = "select * from Review order by GRADE DESC"; //User 테이블에 존재하는 모든 회원들의 정보를 조회하는 쿼리
        return this.jdbcTemplate.query(getReviewQuery,
                (rs, rowNum) -> new GetReviewRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("userName"),
                        rs.getString("orderMenu"),
                        rs.getFloat("grade"),
                        rs.getTimestamp("orderAt"),
                        rs.getTimestamp("postAt"))
                );
    }

    public List<GetReviewRes> getReview(String storeName) {
        String getReviewQuery = "select * from Review where storeName =? order by DESC";
        String getReviewParams = storeName;
        return this.jdbcTemplate.query(getReviewQuery,
                (rs, rowNum) -> new GetReviewRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("userName"),
                        rs.getString("orderMenu"),
                        rs.getFloat("grade"),
                        rs.getTimestamp("orderAt"),
                        rs.getTimestamp("postAt")),
                getReviewParams);
    }


    // 20. 리뷰 작성
    public int createReview(PostReviewReq postReviewReq) {
        String createReviewQuery = "insert into Review (userName, storeName, orderMenu, grade, comment, orderAt) VALUES (?,?,?,?,?,?)";
        Object[] createReviewParams = new Object[]{postReviewReq.getUserName(), postReviewReq.getStoreName(), postReviewReq.getOrderMenu(), postReviewReq.getGrade(), postReviewReq.getComment()};
        this.jdbcTemplate.update(createReviewQuery, createReviewParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery, int.class);
    }
}
