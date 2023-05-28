package com.umc.carrotmarket.src.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.math.BigInteger;
import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetItemRes {
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


}
