package com.umc.carrotmarket.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private BigInteger userNo;
    private String phone;
    private String nickname;
    @Nullable
    private String profileImg;
    @Nullable
    private String email;
    private BigInteger regionIdx;
}
