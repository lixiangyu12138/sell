package com.liwenwen.sell.controller;

import com.liwenwen.sell.pojo.ProductCategory;
import com.liwenwen.sell.pojo.ProductInfo;
import com.liwenwen.sell.service.Impl.CategoryServiceImpl;
import com.liwenwen.sell.service.Impl.ProductServiceImpl;
import com.lly835.bestpay.rest.type.Get;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class SellerProductCategoryController {
    @Autowired
    private CategoryServiceImpl categoryService;
    @GetMapping("/list")
    public  String findAll(Map<String,Object> map){
        List<ProductCategory> all = categoryService.findAll();
        System.out.println(all);
        map.put("all",all);
        return "Category/list";
    }
    @GetMapping("/update")
    public String update( Integer categoryId,
                          Map<String,Object> map){
        ProductCategory one = categoryService.findOne(categoryId);
        map.put("category",one);
        map.put("add","/sell/seller/category/update");
        return "Category/update";
    }
    @GetMapping("/index")
    public String add(Map<String,Object> map){
        map.put("add","/sell/seller/category/save");
        return "Category/update";
    }
    @PostMapping("/update")
    public String update(ProductCategory productCategory,Map<String,Object> map){
        map.put("url","/sell/seller/category/list");
        categoryService.update(productCategory);
        map.put("msg","修改成功");
        return "common/success";
    }
    @PostMapping("/save")
    public String save(ProductCategory productCategory,Map<String,Object> map){
        map.put("url","/sell/seller/category/list");
        categoryService.save(productCategory);
        map.put("msg","添加成功");
        return "common/success";
    }
}
