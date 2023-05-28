package com.umc.carrotmarket.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetItemDetailRes{
    BigInteger itemIdx;
    private BigInteger userIdx;
    private String title;
    @Nullable
    Integer price;
    private Timestamp createdAt;
    private BigInteger categoryIdx;
    private BigInteger regionIdx;
    private String categoryName;
    private String regionName;
    private Integer range;
    private Integer chatNum;
    private Integer likeNum;
    @Nullable
    private String photo;
////
    private String profileImg;
    private List<String> photos;
    private String nickname;
    Integer isSharing;
    Integer isSuggestion;
    @Nullable
    String placeName;
    @Nullable
    String placeLocation;
    Timestamp updatedAt;
}
