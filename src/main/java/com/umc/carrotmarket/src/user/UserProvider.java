package com.umc.carrotmarket.src.user;

import com.umc.carrotmarket.config.BaseException;
import com.umc.carrotmarket.src.user.model.GetUserRes;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.umc.carrotmarket.config.BaseResponseStatus.DATABASE_ERROR;
import static com.umc.carrotmarket.config.BaseResponseStatus.USERS_NON_EXIST_USER;

@Service
@AllArgsConstructor
public class UserProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final UserDao userDao;

    // R(Read)
    public List<GetUserRes> getUsers() throws BaseException {
        try{
            List<GetUserRes> getUserRes = userDao.getUsers();
            return getUserRes;
        }
        catch (Exception exception) {
            logger.error("Error!", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserRes> getUsersByPhone(String phone) throws BaseException {
        try{
            List<GetUserRes> getUserRes = userDao.getUsersByPhone(phone);
            return getUserRes;
        }
        catch (Exception exception) {
            logger.error("Error!", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetUserRes getUser(String userNo) throws BaseException {
        // Validation - 존재하지 않는 유저
        if (checkUserNo(userNo) == 0) {
            throw new BaseException(USERS_NON_EXIST_USER);
        }

        try{
            GetUserRes getUserRes = userDao.getUser(userNo);
            return getUserRes;
        }
        catch (Exception exception) {
            logger.error("Error!", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // Check
    public int checkPhone(String phone) throws BaseException {
        try {
            return userDao.checkPhone(phone);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkUserNo(String userNo) throws BaseException {
        try {
            return userDao.checkUserNo(userNo);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkUserIdx(String userIdx) throws BaseException {
        try {
            return userDao.checkUserIdx(userIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
