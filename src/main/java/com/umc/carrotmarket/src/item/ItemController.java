package com.umc.carrotmarket.src.item;

import com.umc.carrotmarket.config.BaseException;
import com.umc.carrotmarket.config.BaseResponse;
import com.umc.carrotmarket.src.item.model.*;
import com.umc.carrotmarket.utils.ValidationRegex;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.umc.carrotmarket.config.BaseResponseStatus.*;

@Tag(name = "아이템")

@RequestMapping("/items")
@RestController
@RequiredArgsConstructor
public class ItemController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ItemProvider itemProvider;

    @Autowired
    private final ItemService itemService;

    /**
     * 아이템 목록 조회 API
     * [GET] /items?regionIdx=
     * @return BaseResponse<List<GetItemRes>>
     * */
    @Operation(summary = "아이템 목록 조회 API")
    @GetMapping("")
    public BaseResponse<List<GetItemRes>> getItems(@RequestParam String regionIdx, @RequestParam(required = false) String categoryIdx) {
        // Validation - 필수값
        if (regionIdx == null) {
            return new BaseResponse<>(ITEMS_EMPTY_REGION);
        }

        try {
            if (categoryIdx == null) {
                List<GetItemRes> getItemRes = itemProvider.getItems(regionIdx);
                return new BaseResponse<>(getItemRes);
            }

            List<GetItemRes> getItemRes = itemProvider.getItemsByCategoryIdx(regionIdx, categoryIdx);
            return new BaseResponse<>(getItemRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 아이템 상세 조회 API
     * [GET] /items/:itemIdx
     * @return BaseResponse<GetItemRes>
     * */
    @Operation(summary = "아이템 상세 조회 API")
    @GetMapping("{itemIdx}")
    public BaseResponse<GetItemDetailRes> getItemDetail(@PathVariable String itemIdx) {
        // Validation - 인덱스 형식
        if (!ValidationRegex.isRegexBigInteger(itemIdx)) {
            return new BaseResponse<>(INVALID_IDX);
        }

        try {
            GetItemDetailRes getItemDetailRes = itemProvider.getItemDetail(itemIdx);
            return new BaseResponse<>(getItemDetailRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    /**
     * 아이템 등록 API
     * [POST] /items
     * @return BaseResponse<PostItemRes>
     * */
    @Operation(summary = "아이템 등록 API")
    @PostMapping("")
    public BaseResponse<PostItemRes> createItem(@RequestBody PostItemReq postItemReq) {
        // Validation - 필수 입력 항목
        if (postItemReq.getUserIdx() == null || postItemReq.getTitle() == null || postItemReq.getDesc() == null || postItemReq.getCategoryIdx() == null || postItemReq.getRegionIdx() == null) {
            return new BaseResponse<>(ITEMS_EMPTY_MANDATORY);
        }

        try {
            PostItemRes postItemRes = itemService.createItem(postItemReq);
            return new BaseResponse<>(postItemRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 아이템 수정 API
     * [PATCH] /items/:itemIdx
     * @return BaseResponse<String>
     * */

    @Operation(summary = "아이템 수정 API")
    @PutMapping("/{itemIdx}")
    public BaseResponse<String> modifyItem(@PathVariable String itemIdx, @RequestBody PutItemReq putItemReq) {
        // Validation - 인덱스 형식
        if (!ValidationRegex.isRegexBigInteger(itemIdx)) {
            return new BaseResponse<>(INVALID_IDX);
        }

        try {
            itemService.modifyItem(itemIdx, putItemReq);

            String result = itemIdx + "번 아이템 수정에 성공하였습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 아이템 삭제 API
     * [DELETE] /items
     * @return BaseResponse<PostItemRes>
     * */
    @Operation(summary = "아이템 삭제 API")
    @DeleteMapping("/{itemIdx}")
    public BaseResponse<String> deleteItem(@PathVariable String itemIdx) {
        // Validation - 인덱스 형식
        if (!ValidationRegex.isRegexBigInteger(itemIdx)) {
            return new BaseResponse<>(INVALID_IDX);
        }

        try {
            itemService.deleteItem(itemIdx);

            String result = itemIdx + "번 아이템 삭제에 성공하였습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 아이템 검색 API
     * [GET] /items/search/?searchWord=
     * @return BaseResponse<List<GetItemRes>>
     * */
    @Operation(summary = "아이템 검색 API")
    @GetMapping("/search")
    public BaseResponse<List<GetItemRes>> searchItems(@RequestParam String regionIdx, @RequestParam String searchWord) {
        // Validation - 필수값
        if (regionIdx == null || searchWord == null) {
            return new BaseResponse(ITEMS_EMPTY_REGION_AND_SEARCH_WORD);
        }

        try {
            List<GetItemRes> getItemRes = itemProvider.getItemsBySearchWord(regionIdx, searchWord);
            return new BaseResponse<>(getItemRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}

