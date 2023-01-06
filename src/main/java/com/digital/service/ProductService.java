package com.digital.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.digital.schema.Product;
import com.digital.sql.mapper.ProductMapper;
import com.digital.sql.vo.ProductVO;

@Component
public class ProductService {

	@Resource
	ProductMapper productMapper;

	// 상품 검색
	public Product searchProduct(String productName) {

		ProductVO productVO = productMapper.getProduct(productName);
		Product product = new Product();

		if (productVO != null) {
			product.setProductId(productVO.getProductId());
			product.setCategoryId(productVO.getCategoryId());
			product.setProductName(productVO.getProductName());
			product.setPrice(productVO.getPrice());
		}

		return product;
	}

	// 상품 등록
	public boolean insertProduct(Product product) throws Exception {

		ProductVO productVO = new ProductVO();

		try {
			// 상품 중복 여부
			if (productMapper.getProduct(product.getProductName()) != null) {
				throw new Exception("등록된 상품입니다.");
			}

			product.setProductId(System.currentTimeMillis());

			productVO.setProductId(product.getProductId());
			productVO.setCategoryId(product.getCategoryId());
			productVO.setProductName(product.getProductName());
			productVO.setPrice(product.getPrice());

			productMapper.setProduct(productVO);

			return true;
		} catch (Exception e) {
			throw e;
		}
	}

}
