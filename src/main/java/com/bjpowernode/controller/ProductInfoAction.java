package com.bjpowernode.controller;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.ProductType;
import com.bjpowernode.pojo.vo.ProductInfoVo;
import com.bjpowernode.service.ProductIfoService;
import com.bjpowernode.utils.FileNameUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/prod")
public class ProductInfoAction {

    //分页的一页多少行
    public static final int PAGE_SIZE = 5;

    //异步ajax的图片名称
    String saveFileName = "";

    @Autowired
    ProductIfoService productIfoService;

    @RequestMapping("/getAll")
    public String getAll(HttpServletRequest request){
        List<ProductInfo> list = productIfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }

    @RequestMapping("/split")
    public String split(HttpServletRequest request,ProductInfoVo vo){
        saveFileName = "";
//        System.out.println("if pageNum = "+page)
        if (request.getSession().getAttribute("vo")!=null)vo= (ProductInfoVo) request.getSession().getAttribute("vo");
        request.getSession().removeAttribute("vo");
        if (vo.getPage() == null)vo.setPage(1);
//        System.out.println("if end pageNum = "+page);
        PageInfo pageInfo = productIfoService.splitPageVo(vo, PAGE_SIZE);
        request.setAttribute("info",pageInfo);
        request.setAttribute("vo",vo);
        return "product";
    }

    @RequestMapping("/ajaxsplit")
    @ResponseBody
    public void ajaxsplit(ProductInfoVo vo, HttpSession session){
//        System.out.println(vo);
        if(vo.getPage()==null||vo.getPage()==0)vo.setPage(1);
        PageInfo pageInfo = productIfoService.splitPageVo(vo, PAGE_SIZE);
        session.setAttribute("info",pageInfo);
    }

    @RequestMapping("/condition")
    @ResponseBody
    public void condition(ProductInfoVo vo, HttpSession session){
        PageHelper.startPage(1,PAGE_SIZE);
        List<ProductInfo> list = productIfoService.selectCondition(vo);
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        session.setAttribute("info",pageInfo);
    }

    @RequestMapping("/ajaxImg")
    @ResponseBody
    public String ajaxImg(MultipartFile pimage,
                          HttpServletRequest request){
        //获取重组之后的图片名称
        System.out.println(pimage.getOriginalFilename());
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        String path = request.getServletContext().getRealPath("/image_big");
        try {
            pimage.transferTo(new File(path+File.separator+saveFileName));
            System.out.println(path+File.separator+saveFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveFileName;
    }

    @RequestMapping("/save")
    public String save(ProductInfo info, HttpServletRequest request){
        if (saveFileName == "") {
            return "forward:/prod/split.action";
        }
        info.setpImage(saveFileName);
//        info.setpDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        info.setpDate(new Date());

        System.out.println(info);

        int num = -1;
        num = productIfoService.save(info);
//        System.out.println("num = "+num);
        if (num > 0) {
            request.setAttribute("msg", "添加成功！");
        } else {
            request.setAttribute("msg", "添加失败！");
        }
        saveFileName = "";
//        request.getRequestDispatcher().forward();
        return "forward:/prod/split.action";
    }

    @RequestMapping("/one")
    public String one(int pid, ProductInfoVo vo, Model model,HttpSession session){
        ProductInfo info = productIfoService.getById(pid);
        model.addAttribute("prod",info);
        session.setAttribute("vo",vo);
        return "update";
    }
    @RequestMapping("/update")
    public String update(ProductInfo info,HttpServletRequest request){
        if (saveFileName != "")info.setpImage(saveFileName);
        int num = -1;
        num = productIfoService.update(info);
        if (num > 0){
            request.setAttribute("msg","更新成功");
        } else {
            request.setAttribute("msg","更新失败");
        }
        saveFileName = "";
        return "forward:/prod/split.action";
    }

    @RequestMapping(value = "/delete",produces = "text/html;charset=utf-8")
    @ResponseBody
    public Object delete(int pid,ProductInfoVo vo,HttpServletRequest request){
        String msg = "";
        int num = -1;
        try{
            num = productIfoService.delete(pid);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (num > 0){
            msg="删除成功！";
        }else {
            msg="删除失败！";
        }
        if(vo.getPage()==null||vo.getPage()==0)vo.setPage(1);
        if (vo.getPage()>productIfoService.splitPageVo(vo,PAGE_SIZE).getPages())vo.setPage(vo.getPage()-1);
        PageInfo pageInfo = productIfoService.splitPageVo(vo, PAGE_SIZE);
        request.getSession().setAttribute("info",pageInfo);
        return msg;
    }


//    @RequestMapping("/deleteBatch")
//    @ResponseBody
//    public String deleteBatch(String[] ids,int page,HttpServletRequest request){
//        int num = -1;
//        num = productIfoService.deleteBatch(ids);
//        System.out.println(num);
//        System.out.println(page);
//        if (num > 0){
//            request.setAttribute("msg","多选删除成功！");
//        }else {
//            request.setAttribute("msg","多选删除失败！");
//        }
//        PageInfo pageInfo = productIfoService.splitPage(1, PAGE_SIZE);
//        if (page>pageInfo.getPages())page--;
//        return "forward:/prod/split.action?page"+page;
//    }

    @RequestMapping("/deleteBatch")
    public String deleteBatch(String[] ids,ProductInfoVo vo,HttpServletRequest request){
        int num = -1;
        try{
            num = productIfoService.deleteBatch(ids);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (num > 0){
            request.setAttribute("msg","多选删除成功！");
        }else {
            request.setAttribute("msg","多选删除失败！");
        }
//        if(vo.getPage()==null||vo.getPage()==0)vo.setPage(1);
//        PageInfo pageInfo = productIfoService.splitPageVo(vo, PAGE_SIZE);
//        session.setAttribute("info",pageInfo);
        return "forward:/prod/ajaxdeletesplit.action?vo="+vo;
    }

    @RequestMapping(value = "/ajaxdeletesplit",produces = "text/html;charset=utf-8")
    @ResponseBody
    public Object ajaxdeletesplit(ProductInfoVo vo, HttpServletRequest request){
        System.out.println("========"+request.getAttribute("msg"));
        if(vo.getPage()==null||vo.getPage()==0)vo.setPage(1);
        if(vo.getPage()>productIfoService.splitPageVo(vo,PAGE_SIZE).getPages())vo.setPage(vo.getPage()-1);
        PageInfo pageInfo = productIfoService.splitPageVo(vo, PAGE_SIZE);
        request.getSession().setAttribute("info",pageInfo);
        return request.getAttribute("msg");
    }

}
