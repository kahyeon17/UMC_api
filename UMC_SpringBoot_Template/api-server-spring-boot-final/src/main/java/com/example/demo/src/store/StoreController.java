package com.example.demo.src.store;

import com.example.demo.src.store.model.GetStoreRes;
import com.example.demo.src.store.model.PatchStoreAddressReq;
import com.example.demo.src.store.model.PatchStoreReq;
import com.example.demo.src.store.model.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/stores")

public class StoreController {

    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log를 남기기: 일단은 모르고 넘어가셔도 무방합니다.

    @Autowired
    private final StoreProvider storeProvider;
    @Autowired
    private final StoreService storeService;
    @Autowired
    private final JwtService jwtService;


    public StoreController(StoreProvider storeProvider, StoreService storeService, JwtService jwtService) {
        this.storeProvider = storeProvider;
        this.storeService = storeService;
        this.jwtService = jwtService;
    }


    /**
     * 5. 매장 정보 조회 API
     * [GET] /stores/:storeIdx
     */
    @ResponseBody
    @GetMapping("/{storeIdx}")
    public BaseResponse<GetStoreRes> getStore(@PathVariable("storeIdx") int storeIdx) {
        try {
            GetStoreRes getStoreRes = storeProvider.getStore(storeIdx);
            return new BaseResponse<>(getStoreRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 7. 매장 정보 수정 - 찜 개수 1 증가
     * [PATCH] /stores/? Name=
     */
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> modifyStoreDibs(@RequestParam(required = false) String name, @RequestBody Store store) {
        try {
//            //jwt에서idx추출.
//            int storeNameByJwt = jwtService.getName();
//            //userIdx와 접근한 유저가 같은지 확인
//            if (name != storeNameByJwt) {
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }

            PatchStoreReq patchStoreReq = new PatchStoreReq(name, store.getDibs());
            storeService.modifyStoreDibs(patchStoreReq);

            String result = "찜 개수가 증가되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 8. 매장 정보 수정 - 주소
     * [PATCH] /stores/:{storeIdx}
     */
    @ResponseBody
    @PatchMapping("/{storeIdx}")
    public BaseResponse<String> modifyStoreAddress(@PathVariable("storeIdx") int storeIdx, @RequestBody Store store) {
        try {
//            //jwt에서idx추출.
//            int storeNameByJwt = jwtService.getName();
//            //userIdx와 접근한 유저가 같은지 확인
//            if (name != storeNameByJwt) {
//                return new BaseResponse<>(INVALID_USER_JWT);
//            }

            PatchStoreAddressReq patchStoreAddressReq = new PatchStoreAddressReq(storeIdx, store.getAddress());
            storeService.modifyStoreAddress(patchStoreAddressReq);

            String result = "매장 주소가 변경되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 10. 매장 by TypeName 조회 API
     * [GET] /stores/? TypeName=
     */
    //Query String
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetStoreRes>> getStoreByType(@RequestParam(required = false) String typeName) {
        try {
            if (typeName == null) {
                List<GetStoreRes> getStoreRes = storeProvider.getStore();
                return new BaseResponse<>(getStoreRes);
            }
            // query string인 typeName이 있을 경우, 조건을 만족하는 메뉴들을 불러온다.
            List<GetStoreRes> getStoreRes = storeProvider.getStoreByType(typeName);
            return new BaseResponse<>(getStoreRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 11. 매장 by Address 조회 API
     * [GET] /stores/? Address=
     */
    //Query String
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetStoreRes>> getStoreByAddress(@RequestParam(required = false) String address) {
        try {
            if (address == null) {
                List<GetStoreRes> getStoreRes = storeProvider.getStore();
                return new BaseResponse<>(getStoreRes);
            }
            // query string인 address이 있을 경우, 조건을 만족하는 메뉴들을 불러온다.
            List<GetStoreRes> getStoreRes = storeProvider.getStoreByAddress(address);
            return new BaseResponse<>(getStoreRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
