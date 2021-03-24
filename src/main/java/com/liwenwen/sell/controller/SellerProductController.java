package com.liwenwen.sell.controller;

import com.liwenwen.sell.dto.OrderDto;
import com.liwenwen.sell.pojo.ProductCategory;
import com.liwenwen.sell.pojo.ProductInfo;
import com.liwenwen.sell.service.CategoryService;
import com.liwenwen.sell.service.ProductService;
import com.lly835.bestpay.rest.type.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
@Slf4j
@Service
public class SellerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public String findAll(@RequestParam(value="page",defaultValue="1") Integer page,
                          @RequestParam(value="size",defaultValue="10")Integer size,
                          Map<String,Object> map){
        PageRequest pageRequest = PageRequest.of(page-1,size);
        Page<ProductInfo> all = productService.findAll(pageRequest);
        map.put("all",all);
        map.put("page",page);
        return "product/list";
    }
    @GetMapping("/upSale")
    public String upSale(@RequestParam("productId") String productId,
                          Map<String,Object> map){
        try{
            map.put("url","/sell/seller/product/list");
            ProductInfo productInfo = productService.upSale(productId);
            map.put("msg","上架成功!");
            return "common/success";
        }catch (Exception e){
            log.info("【上架商品】 查询不到商品");
            map.put("msg",e.getMessage());
            return "common/error";
        }
    }
    @GetMapping("/downSale")
    public String downSale(@RequestParam("productId") String productId,
                         Map<String,Object> map){
        try{
            map.put("url","/sell/seller/product/list");
            map.put("Sale","downSale");
            productService.downSale(productId);
            map.put("msg","下架成功!");
            return "common/success";
        }catch (Exception e){
            log.info("【下架商品】 查询不到商品");
            map.put("msg",e.getMessage());
            return "common/error";
        }
    }
    @GetMapping("/update")
    public String update( String productId,
                         Map<String,Object> map){
        ProductInfo one = productService.findOne(productId);
        map.put("product",one);
        List<ProductCategory> all = categoryService.findAll();
        map.put("categoryTypes",all);
        map.put("add","/sell/seller/product/update");
        return "product/update";
        }
    @GetMapping("/index")
    public String add(Map<String,Object> map){
        List<ProductCategory> all = categoryService.findAll();
        map.put("categoryTypes",all);
        map.put("add","/sell/seller/product/save");
        return "product/update";
    }
    @PostMapping("/update")
    public String update(ProductInfo productInfo,Map<String,Object> map){
        map.put("url","/sell/seller/product/list");
        log.info("【修改商品】productInfo={}",productInfo);
        productService.update(productInfo);
        map.put("msg","修改成功");
        return "common/success";
    }
    @PostMapping("/save")
    public String save(ProductInfo productInfo,Map<String,Object> map){
        map.put("url","/sell/seller/product/list");
        log.info("【添加商品】productInfo={}",productInfo);
        productService.save(productInfo);
        map.put("msg","添加成功");
        return "common/success";
    }

}
