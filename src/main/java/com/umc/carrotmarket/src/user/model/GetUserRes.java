package com.umc.carrotmarket.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
//    private BigInteger userIdx;
    private String phone;
    private String nickname;
//    @Nullable
//    private String profileImg;
//    @Nullable
//    private String email;
//    private BigInteger regionIdx;
}
