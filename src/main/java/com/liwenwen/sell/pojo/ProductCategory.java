package com.liwenwen.sell.pojo;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

@Entity
/**
 * org.hibernate.lazyinitializationexception could not initialize proxy - no session
 * @Proxy(lazy = false)
 */
@Proxy(lazy = false)
//实时改变修改时间
@DynamicUpdate
@DynamicInsert
@Data

public class ProductCategory {
    /*类目id*/
    @Id
    /**
     * Table 'XXX.hibernate_sequence' doesn't exist
     * 这个是主键自增长策略问题。
     *
     * 将ID生成略组改成@GeneratedValue(strategy = GenerationType.IDENTITY).
     *
     *
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    /*类目名*/
    private String categoryName;
    /*类目号*/
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;


    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
