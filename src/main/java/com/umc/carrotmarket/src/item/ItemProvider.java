package com.umc.carrotmarket.src.item;

import com.umc.carrotmarket.config.BaseException;
import com.umc.carrotmarket.src.item.model.GetItemDetailRes;
import com.umc.carrotmarket.src.item.model.GetItemRes;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.umc.carrotmarket.config.BaseResponseStatus.*;

@Service
@AllArgsConstructor
public class ItemProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ItemDao itemDao;

    // R(Read)
    public List<GetItemRes> getItems(String regionIdx) throws BaseException {
        // Validation - 유효한 지역
        if (checkRegionIdx(regionIdx) == 0) {
            throw new BaseException(ITEMS_INVALID_REGION);
        }

        try {
            List<GetItemRes> getItemRes = itemDao.getItemsByRegionIdx(regionIdx);
            return getItemRes;
        } catch (Exception exception) {
            logger.error("Error!", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetItemRes> getItemsByCategoryIdx(String regionIdx, String categoryIdx) throws BaseException {
        // Validation - 유효한 지역, 카테고리
        if (checkRegionIdx(regionIdx) == 0) {
            throw new BaseException(ITEMS_INVALID_REGION);
        }

        if (checkCategoryIdx(categoryIdx) == 0) {
            throw new BaseException(ITEMS_INVALID_CATEGORY);
        }

        try {
            List<GetItemRes> getItemRes = itemDao.getItemsByCategoryIdx(regionIdx, categoryIdx);
            return getItemRes;
        } catch (Exception exception) {
            logger.error("Error!", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetItemRes> getItemsBySearchWord(String regionIdx, String searchWord) throws BaseException {
        try {
            List<GetItemRes> getItemRes = itemDao.getItemsBySearchWord(regionIdx, searchWord);
            return getItemRes;
        } catch (Exception exception) {
            logger.error("Error!", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetItemDetailRes getItemDetail(String itemIdx) throws BaseException {
        List<GetItemDetailRes> getItemDetailRes = itemDao.getItemDetail(itemIdx);

        // Validation - 존재하지 않는 아이템 아이디
        if (getItemDetailRes.size() < 1) {
            throw new BaseException(ITEMS_NON_EXIST_ITEM_ID);
        }

        return getItemDetailRes.get(0);
    }

    // Check
    public int checkItemIdx(String itemIdx) throws BaseException{
        try{
            return itemDao.checkItemIdx(itemIdx);
        } catch (Exception exception){
            logger.error("Error!", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkRegionIdx(String regionIdx) throws BaseException{
        try{
            return itemDao.checkRegionIdx(regionIdx);
        } catch (Exception exception){
            logger.error("Error!", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkCategoryIdx(String categoryIdx) throws BaseException{
        try{
            return itemDao.checkCategoryIdx(categoryIdx);
        } catch (Exception exception){
            logger.error("Error!", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
