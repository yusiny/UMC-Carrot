package com.umc.carrotmarket.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class PostItemReq {
    private BigInteger userIdx;
    private String title;
    private BigInteger categoryIdx;
    private BigInteger regionIdx;
    @Nullable
    private Integer price;
    @Nullable
    private String placeName;
    @Nullable
    private String placeLocation;
    private String desc;
    private Integer range = 3;
    private Integer isSharing = -1;
    private Integer isSuggestion = -1;
    private Integer itemHasPhoto = -1;
}
