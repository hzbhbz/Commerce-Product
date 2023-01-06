package com.digital.sql.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.digital.sql.vo.ProductVO;

@Mapper
public interface ProductMapper {

	public ProductVO getProduct(String productName);

	public void setProduct(ProductVO productVO);

}