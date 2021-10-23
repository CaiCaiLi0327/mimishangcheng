package com.bjpowernode;

import com.bjpowernode.mapper.AdminMapper;
import com.bjpowernode.mapper.ProductInfoMapper;
import com.bjpowernode.pojo.Admin;
import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoVo;
import com.bjpowernode.utils.MD5Util;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class Mytest {

    @Autowired
    AdminMapper adminMapper;
    @Autowired
    ProductInfoMapper mapper;
    @Test
    public void test() {
        ProductInfoVo vo = new ProductInfoVo();
        vo.setPname("红米");
        vo.setTypeid(1);
        vo.setLprice(1000);
        vo.setHprice(2000);
        List<ProductInfo> productInfos = mapper.selectCondition(vo);
        productInfos.forEach(productInfo-> System.out.println(productInfo));
    }

    @Test
    public void testMD5(){
        System.out.println(MD5Util.getMD5("000000"));
    }


    @Test
    public void select(){
        Admin admin = new Admin();
        admin.setaName("admin");
        admin.setaPass("c984aed014aec7623a54f0591da07a85fd4b762d");
        int test = adminMapper.selectByNameAndPwd(admin);
        System.out.println(test);
    }
    @Test
    public void zw(){
        System.out.println("测试");
    }
}
