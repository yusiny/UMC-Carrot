package com.umc.carrotmarket.src.user;

import com.umc.carrotmarket.config.BaseException;
import com.umc.carrotmarket.config.BaseResponse;
import com.umc.carrotmarket.config.BaseResponseStatus;
import com.umc.carrotmarket.src.user.model.GetUserRes;
import com.umc.carrotmarket.src.user.model.PatchUserReq;
import com.umc.carrotmarket.src.user.model.PostUserReq;
import com.umc.carrotmarket.src.user.model.PostUserRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static com.umc.carrotmarket.config.BaseResponseStatus.INVALID_USER_NO;
import static com.umc.carrotmarket.config.BaseResponseStatus.USERS_EMPTY_NICKNAME;
import static com.umc.carrotmarket.utils.ValidationRegex.isRegexBigInteger;
import static com.umc.carrotmarket.utils.ValidationRegex.isRegexPhone;

@Tag(name = "유저")
@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;

    @Autowired
    private final UserService userService;

    /**
     * 유저 목록 조회 API
     * [GET] /users
     * @return BaseResponse<List<GetUserRes>>
     * */
    @Operation(summary = "유저 목록 조회 API")
    @GetMapping("")
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String phone) {
        // Validation - 전화번호 형식
        if (phone != null && !isRegexPhone(phone)) {
            return new BaseResponse<>(BaseResponseStatus.USERS_INVALID_PHONE);
        }

        try{
            if (phone == null) {
                List<GetUserRes> getUsersRes = userProvider.getUsers();
                return new BaseResponse<>(getUsersRes);
            }

            List<GetUserRes> getUsersRes = userProvider.getUsersByPhone(phone);
            return new BaseResponse<>(getUsersRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 조회 API
     * [GET] /users/:userNo
     * @return BaseResponse<GetUserRes>
     * */
    @Operation(summary = "유저 조회 API")
    @GetMapping("/{userNo}")
    public BaseResponse<GetUserRes> getUser(@PathVariable("userNo") String userNo) {
        // Validation - 유저 번호 형식
        if (!isRegexBigInteger(userNo)) {
            return new BaseResponse<>(INVALID_USER_NO);
        }

        try{
            GetUserRes getUserRes = userProvider.getUser(userNo);
            return new BaseResponse<>(getUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     * */
    @Operation(summary = "회원가입 API")
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        // Validation - 필수값 여부, 형식
        if (postUserReq.getPhone() == null || postUserReq.getNickname() == null) {
            return new BaseResponse<>(BaseResponseStatus.USERS_EMPTY_PHONE_OR_NICKNAME);
        }

        if (!isRegexPhone(postUserReq.getPhone())) {
            return new BaseResponse<>(BaseResponseStatus.USERS_INVALID_PHONE);
        }

        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 프로필 수정 API
     * [PATCH] /users/{userNo}
     * @return BaseResponse<String>
     * */
    @Operation(summary = "프로필 수정 API")
    @PatchMapping("/{userNo}")
    public BaseResponse<String> modifyUser(@PathVariable String userNo, @RequestBody PatchUserReq patchUserReq) {
        // Validation - 형식, 필수값 여부
        if (!isRegexBigInteger(userNo)) {
            return new BaseResponse<>(INVALID_USER_NO);
        }

        if (patchUserReq.getNickname() == null) {
            return new BaseResponse<>(USERS_EMPTY_NICKNAME);
        }

        try {
            userService.modifyUser(userNo, patchUserReq);

            String result = userNo+ " 유저 정보 수정을 성공하였습니다.";
            return new BaseResponse(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 탈퇴 API
     * [DELETE] /users/{userNo}
     * @return BaseResponse<String>
     * */
    @Operation(summary = "회원 탈퇴 API")
    @DeleteMapping("/{userNo}")
    public BaseResponse<String> modifyUser(@PathVariable String userNo) {// Validation - 형식, 필수값 여부
        if (!isRegexBigInteger(userNo)) {
            return new BaseResponse<>(INVALID_USER_NO);
        }

        try {
            userService.deleteUser(userNo);

            String result = userNo+ " 유저 삭제를 성공하였습니다.";
            return new BaseResponse(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
