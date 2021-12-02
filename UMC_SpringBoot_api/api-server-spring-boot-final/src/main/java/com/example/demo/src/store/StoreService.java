package com.example.demo.src.store;


import com.example.demo.config.BaseException;
import com.example.demo.src.store.model.PatchStoreAddressReq;
import com.example.demo.src.store.model.PatchStoreReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;
@Service

public class StoreService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************
    private final StoreDao storeDao;
    private final StoreProvider storeProvider;
    private final JwtService jwtService;


    @Autowired //readme 참고
    public StoreService(StoreDao storeDao, StoreProvider storeProvider, JwtService jwtService) {
        this.storeDao = storeDao;
        this.storeProvider = storeProvider;
        this.jwtService = jwtService;

    }


    // 7. 매장 정보 수정 - 찜 개수 1 증가
    public void modifyStoreDibs(PatchStoreReq patchStoreReq) throws BaseException {
        try {
            int result = storeDao.modifyStoreDibs(patchStoreReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메시지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_STOREDIBS);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }


    // 8. 매장 정보 수정 - 주소
    public void modifyStoreAddress(PatchStoreAddressReq patchStoreAddressReq) throws BaseException {
        try {
            int result = storeDao.modifyStoreAddress(patchStoreAddressReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메시지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_ADDRESS);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
