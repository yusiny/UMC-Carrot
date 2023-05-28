package com.umc.carrotmarket.src.item;

import com.umc.carrotmarket.src.item.model.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.util.List;

@Repository
public class ItemDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // R(Read)
    public List<GetItemRes> getItemsByRegionIdx(String regionIdx){
        String getItemsQuery = "SELECT I.itemIdx, I.userIdx, I.title, I.createdAt, I.`itemHasPhoto`, I.price, C.categoryName, C.categoryIdx, R.regionIdx, R.regionName, I.`range`, IM.chatNum, IM.likeNum, IM.chatNum" +
                " FROM Item I" +
                " JOIN Category C on I.categoryIdx = C.categoryIdx" +
                " JOIN Region R on I.regionIdx = R.regionIdx" +
                " JOIN ItemMeta IM on I.itemIdx = IM.itemIdx " +
                " WHERE I.regionIdx = " + regionIdx;

        return getGetItemRes(getItemsQuery);
    }

    public List<GetItemRes> getItemsByCategoryIdx(String regionIdx, String categoryIdx){
        String getItemsByCategoryIdxQuery = "SELECT I.itemIdx, I.userIdx, I.title, I.createdAt, I.`itemHasPhoto`, I.price, C.categoryName, C.categoryIdx, R.regionIdx, R.regionName, I.`range`, IM.chatNum, IM.likeNum, IM.chatNum" +
                " FROM Item I" +
                " JOIN Category C on I.categoryIdx = C.categoryIdx" +
                " JOIN Region R on I.regionIdx = R.regionIdx" +
                " JOIN ItemMeta IM on I.itemIdx = IM.itemIdx " +
                " WHERE I.regionIdx = " + regionIdx + " AND I.categoryIdx = " + categoryIdx;

        return getGetItemRes(getItemsByCategoryIdxQuery);
    }

    public List<GetItemRes> getItemsBySearchWord(String regionIdx, String searchWord){
        String getItemsBySearchWordQuery = "SELECT I.itemIdx, I.userIdx, I.title, I.createdAt, I.`itemHasPhoto`, I.price, C.categoryName, C.categoryIdx, R.regionIdx, R.regionName, I.`range`, IM.chatNum, IM.likeNum, IM.chatNum" +
                " FROM Item I" +
                " JOIN Category C on I.categoryIdx = C.categoryIdx" +
                " JOIN Region R on I.regionIdx = R.regionIdx" +
                " JOIN ItemMeta IM on I.itemIdx = IM.itemIdx " +
                " WHERE I.regionIdx = " + regionIdx + " AND I.title LIKE '%" + searchWord.trim() + "%'";

        return getGetItemRes(getItemsBySearchWordQuery);
    }

    @NotNull
    private List<GetItemRes> getGetItemRes(String getItemsQuery) {
        return this.jdbcTemplate.query(getItemsQuery,
                (rs,rowNum) -> new GetItemRes(
                        BigInteger.valueOf(rs.getInt("itemIdx")),
                        BigInteger.valueOf(rs.getInt("userIdx")),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getTimestamp("createdAt"),
                        BigInteger.valueOf(rs.getInt("categoryIdx")),
                        BigInteger.valueOf(rs.getInt("regionIdx")),
                        rs.getString("categoryName"),
                        rs.getString("regionName"),
                        rs.getInt("range"),
                        rs.getInt("chatNum"),
                        rs.getInt("likeNum"),
                        null
                ));
    }

    public List<GetItemDetailRes> getItemDetail(String itemIdx) {
        String getItemDetailQuery = "SELECT I.itemIdx, I.userIdx, U.nickname, I.price, U.profileImg, I.title, I.`desc`, I.range, C.categoryIdx, C.categoryName, I.createdAt, R.regionIdx, R.regionName, IM.chatNum, IM.likeNum, IM.chatNum, I.isSharing, I.isSuggestion, I.updatedAt, I.placeName, I.placeLocation" +
                " FROM Item I" +
                " JOIN `User` U on I.userIdx = U.userIdx" +
                " JOIN Region R on I.regionIdx = R.regionIdx" +
                " JOIN Category C on I.categoryIdx = C.categoryIdx" +
                " JOIN ItemMeta IM on I.itemIdx = IM.itemIdx" +
                " WHERE I.itemIdx =" + itemIdx ;


        return this.jdbcTemplate.query(getItemDetailQuery,
                (rs,rowNum) -> new GetItemDetailRes(
                        BigInteger.valueOf(rs.getInt("itemIdx")),
                        BigInteger.valueOf(rs.getInt("userIdx")),

                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getTimestamp("createdAt"),
                        BigInteger.valueOf(rs.getInt("categoryIdx")),
                        BigInteger.valueOf(rs.getInt("regionIdx")),
                        rs.getString("categoryName"),
                        rs.getString("regionName"),
                        rs.getInt("range"),
                        rs.getInt("chatNum"),
                        rs.getInt("likeNum"),

                        null,
                        rs.getString("profileImg"),
                        null,
                        rs.getString("nickname"),
                        rs.getInt("isSharing"),
                        rs.getInt("isSuggestion"),
                        rs.getString("placeName"),
                        rs.getString("placeLocation"),
                        rs.getTimestamp("updatedAt")
                        )
                );
    }

    // C(Create)
    public BigInteger createItem(PostItemReq postItemReq) {
        String createItemQuery =  "INSERT INTO Item (userIdx, title, categoryIdx, regionIdx, price, `desc`, isSharing, isSuggestion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Object[] createItemParams = new Object[]{ postItemReq.getUserIdx(), postItemReq.getTitle(), postItemReq.getCategoryIdx(), postItemReq.getRegionIdx(), postItemReq.getPrice(), postItemReq.getDesc(), postItemReq.getIsSharing(), postItemReq.getIsSharing()};
        this.jdbcTemplate.update(createItemQuery, createItemParams);

        BigInteger itemIdx = this.jdbcTemplate.queryForObject("SELECT last_insert_id()", BigInteger.class);

        String createItemMetaQuery = "INSERT INTO ItemMeta (itemIdx) values (" + itemIdx + ")";
        this.jdbcTemplate.update(createItemMetaQuery);

        return itemIdx;
    }

    // U(Update)
    public int modifyItem(String itemIdx, PutItemReq putItemReq) {
        String modifyItemQuery = "UPDATE Item SET userIdx=?, title=?, categoryIdx=?, regionIdx=?, price=?, `desc`=?, isSharing=?, isSuggestion=?"
                + " WHERE itemIdx = " + itemIdx;
        Object[] modifyItemParams = new Object[]{putItemReq.getUserIdx(), putItemReq.getTitle(), putItemReq.getCategoryIdx(), putItemReq.getRegionIdx(), putItemReq.getPrice(), putItemReq.getDesc(), putItemReq.getIsSharing(), putItemReq.getIsSuggestion()};

        return this.jdbcTemplate.update(modifyItemQuery, modifyItemParams);
    }

    // D(Delete)
    public int deleteItem(String itemIdx) {
        String deleteItemMetaQuery = "DELETE FROM ItemMeta where itemIdx = " + itemIdx;
        this.jdbcTemplate.update(deleteItemMetaQuery);

        String deleteItemQuery = "DELETE FROM Item where itemIdx = " + itemIdx;
        return this.jdbcTemplate.update(deleteItemQuery);
    }

    // Check
    public int checkItemIdx(String itemIdx) {
        String checkItemIdxQuery = "SELECT exists(SELECT itemIdx FROM Item WHERE itemIdx = " + itemIdx + ")";

        return this.jdbcTemplate.queryForObject(checkItemIdxQuery, int.class);
    }

    public int checkRegionIdx(String regionIdx) {
        String checkRegionIdxQuery = "SELECT exists(SELECT regionIdx FROM Region WHERE regionIdx = " + regionIdx + ")";

        return this.jdbcTemplate.queryForObject(checkRegionIdxQuery, int.class);
    }

    public int checkCategoryIdx(String categoryIdx) {
        String checkCategoryIdxQuery = "SELECT exists(SELECT categoryIdx FROM Category WHERE categoryIdx = " + categoryIdx + ")";

        return this.jdbcTemplate.queryForObject(checkCategoryIdxQuery, int.class);
    }
}
