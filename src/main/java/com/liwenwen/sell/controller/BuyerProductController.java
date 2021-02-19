package com.liwenwen.sell.controller;

import com.liwenwen.sell.VO.ProductInfoVo;
import com.liwenwen.sell.VO.ProductVo;
import com.liwenwen.sell.VO.ResultVo;
import com.liwenwen.sell.pojo.ProductCategory;
import com.liwenwen.sell.pojo.ProductInfo;
import com.liwenwen.sell.service.CategoryService;
import com.liwenwen.sell.service.ProductService;
import com.liwenwen.sell.utils.ResultVoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResultVo list(){
        //查询所有上架商品
        List<ProductInfo> upAll = productService.findUpAll();
        //查询类目

        List<Integer> categoryTypeList = upAll.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList=categoryService.findByCatedoryTypeIn(categoryTypeList);


        //数据拼装
        List<ProductVo> productVos = new ArrayList<>();
        for(ProductCategory productCategory:productCategoryList){
            ProductVo productVo= new ProductVo();
            List<ProductInfoVo> productInfoVos=new ArrayList<>();
            productVo.setCategoryType(productCategory.getCategoryType());
            productVo.setCategoryName(productCategory.getCategoryName());

            for (ProductInfo productInfo:upAll){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVo productInfoVo= new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo,productInfoVo);
                    productInfoVos.add(productInfoVo);
                }
            }

            productVo.setProductInfoVoList(productInfoVos);
            productVos.add(productVo);
        }
        return ResultVoUtil.success(productVos);
    }
}
