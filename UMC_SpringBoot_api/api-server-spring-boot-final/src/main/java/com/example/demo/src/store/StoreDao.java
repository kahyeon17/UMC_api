package com.example.demo.src.store;

import com.example.demo.src.store.model.GetStoreRes;
import com.example.demo.src.store.model.PatchStoreAddressReq;
import com.example.demo.src.store.model.PatchStoreReq;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository //  [Persistence Layer에서 DAO를 명시하기 위해 사용]


public class StoreDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {this.jdbcTemplate = new JdbcTemplate(dataSource);}



    // 7. 매장 정보 수정 - 찜 등록 ?Name=
    public int modifyStoreDibs(PatchStoreReq patchStoreReq) {
        String modifyStoreDibsQuery = "update Store set dibs = dibs + 1 where name = ? "; // 해당 storeIdx를 만족하는 Store의 dibs(찜)를 1만큼 늘려준다.
        Object[] modifyStoreDibsParams = new Object[]{patchStoreReq.getName()};
        return this.jdbcTemplate.update(modifyStoreDibsQuery, modifyStoreDibsParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 8. 매장 정보 수정 - 주소 {storeIdx}
    public int modifyStoreAddress(PatchStoreAddressReq patchStoreAddressReq) {
        String modifyStoreAddressQuery = "update Store set address = ? where storeIdx = ? "; // 해당 storeIdx를 만족하는 Store의 dibs(찜)를 1만큼 늘려준다.
        Object[] modifyStoreAddressParams = new Object[]{patchStoreAddressReq.getStoreIdx()};
        return this.jdbcTemplate.update(modifyStoreAddressQuery, modifyStoreAddressParams); // 대응시켜 매핑시켜 쿼리 요청(생성했으면 1, 실패했으면 0)
    }

    // 9. 특정 매장 정보 조회 {storeIdx}
    public GetStoreRes getStore(int storeIdx) {
        String getStoreQuery = "select * from Store where storeIdx = ?";
        int getStoreParams = storeIdx;
        return this.jdbcTemplate.queryForObject(getStoreQuery,
                (rs, rowNum) -> new GetStoreRes(
                        rs.getInt("storeIdx"),
                        rs.getString("typeName"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phoneNum"),
                        rs.getFloat("grade"),
                        rs.getInt("dibs")),
                getStoreParams);
    }

    // 10. 매장 by typeName 정보 조회 - 찜 많은 순
    public List<GetStoreRes> getStore() {
        String getStoreQuery = "select * from Store orders limit 5";
        return this.jdbcTemplate.query(getStoreQuery,
                (rs, rowNum) -> new GetStoreRes(
                        rs.getInt("storeIdx"),
                        rs.getString("typeName"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phoneNum"),
                        rs.getFloat("grade"),
                        rs.getInt("dibs"))
                );
    }

    public List<GetStoreRes> getStoreByType(String typeName) {
        String getStoreByTypeQuery = "select * from Store where typeName = ? order by dibs DESC limt 5";
        String getStoreByTypeParams = typeName;
        return this.jdbcTemplate.query(getStoreByTypeQuery,
                (rs, rowNum) -> new GetStoreRes(
                        rs.getInt("storeIdx"),
                        rs.getString("typeName"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phoneNum"),
                        rs.getFloat("grade"),
                        rs.getInt("dibs")),
                getStoreByTypeParams);
    }


    // 11. 매장 by address 정보 조회 - 배달팁 적은 순
    public List<GetStoreRes> getStoreByAddress(String address) {
        String getStoreByAddressQuery = "select * from Store where address = ? order by deliveryTip";
        String getStoreByAddressParams = address;
        return this.jdbcTemplate.query(getStoreByAddressQuery,
                (rs, rowNum) -> new GetStoreRes(
                        rs.getInt("storeIdx"),
                        rs.getString("typeName"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phoneNum"),
                        rs.getFloat("grade"),
                        rs.getInt("dibs")),
                getStoreByAddressParams);
    }
}


