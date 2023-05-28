package com.umc.carrotmarket.src.user;

import com.umc.carrotmarket.src.user.model.GetUserRes;
import com.umc.carrotmarket.src.user.model.PatchUserReq;
import com.umc.carrotmarket.src.user.model.PostUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // R(Read)
    public List<GetUserRes> getUsers(){
        String getUsersQuery = "SELECT * FROM User";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getString("nickname"),
                        rs.getString("phone")
                ));
    }

    public List<GetUserRes> getUsersByPhone(String email){
        String getUsersByEmailQuery = "SELECT * FROM User WHERE phone = ?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getString("nickname"),
                        rs.getString("phone")
                ),
                getUsersByEmailParams);
    }

    public GetUserRes getUser(String userNo){
        String getUserQuery = "SELECT * FROM User WHERE userNo = " + userNo;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getString("nickname"),
                        rs.getString("phone")
                ));
    }

    public BigInteger getUserIdxFromUserNo(String userNo) {
        String getUserIdxFromUserNoQuery = "SELECT userIdx FROM User WHERE userNo = " + userNo;
        return this.jdbcTemplate.queryForObject(getUserIdxFromUserNoQuery, BigInteger.class);
    }

    // Check
    public int checkPhone (String phone) {
        String checkPhoneQuery = "SELECT exists(SELECT phone FROM User WHERE phone = ?)";
        String checkPhoneParams = phone;

        return this.jdbcTemplate.queryForObject(checkPhoneQuery, int.class, checkPhoneParams);
    }

    public int checkUserNo (String userNo) {
        String checkUserNoQuery = "SELECT exists(SELECT userNo FROM User WHERE userNo = ?)";
        String checkUserNoParams = userNo;

        return this.jdbcTemplate.queryForObject(checkUserNoQuery, int.class, checkUserNoParams);
    }

    public int checkUserIdx (String userIdx) {
        String checkUserNoQuery = "SELECT exists(SELECT userIdx FROM User WHERE userIdx = ?)";
        String checkUserNoParams = userIdx;

        return this.jdbcTemplate.queryForObject(checkUserNoQuery, int.class, checkUserNoParams);
    }



    // C(Create)
    public BigInteger createUser (PostUserReq postUserReq) {
        String createUserQuery = "INSERT INTO User (userNo, phone, nickname) VALUES (uuid_short(), ?, ?)";
        Object[] createUserParams = new Object[]{postUserReq.getPhone(), postUserReq.getNickname()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        BigInteger userIdx = this.jdbcTemplate.queryForObject("SELECT last_insert_id()", BigInteger.class);

        String createUserRegionQuery = "INSERT INTO UserHasRegion (userIdx, regionIdx) VALUES (?, ?)";
        Object[] createUserRegionParams = new Object[]{userIdx, postUserReq.getRegionIdx()};
        this.jdbcTemplate.update(createUserRegionQuery, createUserRegionParams);

        String lastInsertIdQuery = "SELECT userNo FROM User WHERE userIdx = " + userIdx;
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, BigInteger.class);
    }

    // U(Update)
    public int modifyUser(String userNo, PatchUserReq patchUserReq) {
        String modifyUserQuery = "UPDATE User SET nickname = ?, profileImg = ?" + " WHERE userNo = " + userNo;
        Object[] modifyUserParams = new Object[]{patchUserReq.getNickname(), patchUserReq.getProfileImg()};

        return this.jdbcTemplate.update(modifyUserQuery, modifyUserParams);
    }

    // D(Delete)
    public int deleteUser(String userNo) {
        String userIdx = String.valueOf(getUserIdxFromUserNo(userNo));

        String deleteUserRegionQuery = "DELETE FROM UserHasRegion WHERE userIdx = " + userIdx;
        this.jdbcTemplate.update(deleteUserRegionQuery);

        String deleteUserQuery = "DELETE FROM User WHERE userIdx = " + userIdx;
        return this.jdbcTemplate.update(deleteUserQuery);
    }
}
