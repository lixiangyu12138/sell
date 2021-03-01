package com.liwenwen.sell.service.Impl;

import com.liwenwen.sell.dao.ProductInfoDao;
import com.liwenwen.sell.dto.CarDto;
import com.liwenwen.sell.enums.ProductStatusEnum;
import com.liwenwen.sell.enums.ResultEnum;
import com.liwenwen.sell.exception.SellException;
import com.liwenwen.sell.pojo.ProductInfo;
import com.liwenwen.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductInfoDao productInfoDao;

    @Override
    public ProductInfo findOne(String productId) {
        try {
            ProductInfo one = productInfoDao.getOne(productId);
            //System.out.println("======"+one);
            return one;
        }catch (Exception e){
            log.error("【查询商品】商品不存在");
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);

        }

    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoDao.findAll((org.springframework.data.domain.Pageable) pageable);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDao.save(productInfo);
    }

    @Override
    @Transactional
    //加库存
    public void increaseStoke(List<CarDto> carDtoList) {
        for(CarDto carDto :carDtoList) {
            ProductInfo productInfo = productInfoDao.getOne(carDto.getProductId());
            if(productInfo ==null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + carDto.getProductCount();
            productInfo.setProductStock(result);
            productInfoDao.save(productInfo);

        }

    }

    @Override
    @Transactional
    public void decreaseStoke(List<CarDto> carDtoList) {
        for(CarDto carDto:carDtoList){
            ProductInfo productInfo = productInfoDao.getOne(carDto.getProductId());
            if(productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - carDto.getProductCount();
           // System.out.println(result);
            if(result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            productInfoDao.save(productInfo);
        }

    }


}
