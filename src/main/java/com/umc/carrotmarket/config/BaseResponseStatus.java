package com.umc.carrotmarket.config;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : Request 오류, Response 오류
     */
    // Common
    REQUEST_ERROR(false, HttpStatus.BAD_REQUEST.value(), "입력값을 확인해주세요."),
    EMPTY_JWT(false, HttpStatus.UNAUTHORIZED.value(), "JWT를 입력해주세요."),
    INVALID_JWT(false, HttpStatus.UNAUTHORIZED.value(), "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,HttpStatus.FORBIDDEN.value(),"권한이 없는 유저의 접근입니다."),
    RESPONSE_ERROR(false, HttpStatus.NOT_FOUND.value(), "값을 불러오는데 실패하였습니다."),

    INVALID_USER_NO(false, HttpStatus.BAD_REQUEST.value(), "잘못된 형식의 유저 번호입니다."),
    INVALID_IDX(false, HttpStatus.BAD_REQUEST.value(), "잘못된 형식의 인덱스입니다."),


    // [GET] /users
    USERS_EMPTY_USER_ID(false, HttpStatus.BAD_REQUEST.value(), "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    USERS_EXISTS_PHONE(false, HttpStatus.BAD_REQUEST.value(), "중복된 전화번호입니다."),
    USERS_EMPTY_PHONE_OR_NICKNAME(false, HttpStatus.BAD_REQUEST.value(), "전화번호와 닉네임은 필수 입력 항목입니다."),
    USERS_INVALID_PHONE(false, HttpStatus.BAD_REQUEST.value(), "전화번호 형식을 확인해주세요."),

    // [PATCH] /users
    USERS_NON_EXIST_USER(false, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 유저입니다."),
    USERS_EMPTY_NICKNAME(false, HttpStatus.BAD_REQUEST.value(), "닉네임은 필수 입력 항목입니다."),


    // [GET] items
    ITEMS_EMPTY_REGION(false, HttpStatus.BAD_REQUEST.value(), "지역은 필수 입력 항목입니다. "),
    ITEMS_NON_EXIST_ITEM_ID(false, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 아이템 아이디입니다."),
    ITEMS_INVALID_REGION(false, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 지역 인덱스입니다."),
    ITEMS_INVALID_CATEGORY(false, HttpStatus.BAD_REQUEST.value(), "유효하지 않은 카테고리 인덱스입니다."),
    ITEMS_EMPTY_REGION_AND_SEARCH_WORD(false, HttpStatus.BAD_REQUEST.value(), "지역과 검색어는 필수 입력 항목입니다."),


    // [POST] items
    ITEMS_EMPTY_MANDATORY (false, HttpStatus.BAD_REQUEST.value(), "유저 아이디, 제목, 내용, 카테고리, 지역은 필수 입력 항목입니다."),

    /**
     * 50 : Database, Server 오류
     */
    DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),


    // [PATCH]
    MODIFY_FAIL_ITEM(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "아이템 정보 수정을 실패하였습니다."),
    MODIFY_FAIL_USER(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "유저 정보 수정을 실패하였습니다."),


    // [DELETE]
    DELETE_FAIL_ITEM(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "아이템 삭제를 실패하였습니다."),
    DELETE_FAIL_USER(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "유저 삭제를 실패하였습니다.");



    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
