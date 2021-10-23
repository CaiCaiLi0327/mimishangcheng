package com.bjpowernode.mapper;

import com.bjpowernode.pojo.ProductType;

import java.util.List;

public interface ProductTypeMapper {
    int deleteByPrimaryKey(Integer typeId);

    int insert(ProductType record);

    int insertSelective(ProductType record);

    ProductType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(ProductType record);

    int updateByPrimaryKey(ProductType record);

    List<ProductType> getAll();
}