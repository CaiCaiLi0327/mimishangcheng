package com.bjpowernode.service;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductIfoService {
    //显示全部，不分页
    List<ProductInfo> getAll();

    //分页实现
    PageInfo splitPage(int pageNum, int pageSize);

    //增加商品
    int save(ProductInfo info);

    //按主键查询商品
    ProductInfo getById(int pid);

    //更新
    int update(ProductInfo info);

    //删除单个
    int delete(int pid);

    //删除多个
    int deleteBatch(String []ids);

    //多条件查询
    List<ProductInfo> selectCondition(ProductInfoVo vo);

    //多条件查询分页
    PageInfo splitPageVo(ProductInfoVo vo,int pageSize);
}
