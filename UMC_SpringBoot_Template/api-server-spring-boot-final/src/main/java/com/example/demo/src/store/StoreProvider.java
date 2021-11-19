package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.review.model.GetReviewRes;
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

public class StoreProvider {
    private final StoreDao storeDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public StoreProvider(StoreDao storeDao, JwtService jwtService) {
        this.storeDao = storeDao;
        this.jwtService = jwtService;
    }


    // 9. 매장 정보 조회
    public GetStoreRes getStore(int storeIdx) throws BaseException {
        try {
            GetStoreRes getStoreRes = storeDao.getStore(storeIdx);
            return getStoreRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 10. 매장 by typeName 정보 조회 - 찜 많은 순
    public List<GetStoreRes> getStore() throws BaseException {
        try {
            List<GetStoreRes> getStoreRes = storeDao.getStore();
            return getStoreRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetStoreRes> getStoreByType(String typeName) throws BaseException {
        try {
            List<GetStoreRes> getStoreRes = storeDao.getStoreByType(typeName);
            return getStoreRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 11. 매장 by address 정보 조회 - 배달팁 적은 순
    public List<GetStoreRes> getStoreByAddress(String address) throws BaseException {
        try {
            List<GetStoreRes> getStoreRes = storeDao.getStoreByAddress(address);
            return getStoreRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
