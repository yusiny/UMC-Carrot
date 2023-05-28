package com.umc.carrotmarket.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@AllArgsConstructor
public class PatchUserReq {
    private String nickname;
    @Nullable
    private String profileImg;
}
