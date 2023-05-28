package com.umc.carrotmarket.src.item;

import com.umc.carrotmarket.config.BaseException;
import com.umc.carrotmarket.src.item.model.PutItemReq;
import com.umc.carrotmarket.src.item.model.PostItemReq;
import com.umc.carrotmarket.src.item.model.PostItemRes;
import com.umc.carrotmarket.src.user.UserProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.umc.carrotmarket.config.BaseResponseStatus.*;

@Service
@AllArgsConstructor
public class ItemService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ItemDao itemDao;
    private final ItemProvider itemProvider;
    private final UserProvider userProvider;

    // C(Create)
    public PostItemRes createItem(PostItemReq postItemReq) throws BaseException {
        // Validation - 유저 번호
        if (userProvider.checkUserIdx(String.valueOf(postItemReq.getUserIdx())) == 0) {
            throw new BaseException(USERS_NON_EXIST_USER);
        }

        try {
            String itemIdx = String.valueOf(itemDao.createItem(postItemReq));
            return new PostItemRes(itemIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // U(Update)
    public void modifyItem(String itemIdx, PutItemReq putItemReq) throws BaseException {
        // Validation - 아이템 아이디 존재 여부
        if (itemProvider.checkItemIdx(itemIdx) == 0) {
            throw new BaseException(ITEMS_NON_EXIST_ITEM_ID);
        }

        int result = itemDao.modifyItem(itemIdx, putItemReq);
        if (result == 0)
            throw new BaseException(MODIFY_FAIL_ITEM);
    }

    // D(Delete)
    public void deleteItem(String itemIdx) throws BaseException {
        // Validation - 아이템 아이디 존재 여부
        if (itemProvider.checkItemIdx(itemIdx) == 0) {
            throw new BaseException(ITEMS_NON_EXIST_ITEM_ID);
        }

        int result = itemDao.deleteItem(itemIdx);
        if (result == 0)
            throw new BaseException(DELETE_FAIL_ITEM);
    }
}
