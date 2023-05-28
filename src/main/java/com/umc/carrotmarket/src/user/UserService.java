package com.umc.carrotmarket.src.user;

import com.umc.carrotmarket.config.BaseException;
import com.umc.carrotmarket.src.user.model.PatchUserReq;
import com.umc.carrotmarket.src.user.model.PostUserReq;
import com.umc.carrotmarket.src.user.model.PostUserRes;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.umc.carrotmarket.config.BaseResponseStatus.*;

@Service
@AllArgsConstructor
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;

    // C(Create)
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        // Validation - 전화번호 중복
        if (userProvider.checkPhone(postUserReq.getPhone()) == 1) {
            throw new BaseException(USERS_EXISTS_PHONE);
        }

        try {
            String userIdx = String.valueOf(userDao.createUser(postUserReq));
            return new PostUserRes(userIdx);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // U(Update)
    public void modifyUser(String userNo, PatchUserReq patchUserReq) throws BaseException {
        // Validation - 존재하지 않는 유저
        if (userProvider.checkUserNo(userNo) == 0) {
            throw new BaseException(USERS_NON_EXIST_USER);
        }

        int result = userDao.modifyUser(userNo, patchUserReq);
        if (result == 0)
            throw new BaseException(MODIFY_FAIL_USER);
    }

    // D(Delete)
    public void deleteUser(String userNo) throws BaseException {
        // Validation - 존재하지 않는 유저
        if (userProvider.checkUserNo(userNo) == 0) {
            throw new BaseException(USERS_NON_EXIST_USER);
        }

        int result = userDao.deleteUser(userNo);
        if (result == 0)
            throw new BaseException(DELETE_FAIL_USER);
    }
}
