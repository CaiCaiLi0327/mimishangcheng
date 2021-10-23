package com.bjpowernode.mapper;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(Integer pId);
    int deleteBatch(String[] ids);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(Integer pId);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    List<ProductInfo> selectAll();

    List<ProductInfo> selectAllOrderKeyDesc();

    List<ProductInfo> selectCondition(ProductInfoVo vo);

}