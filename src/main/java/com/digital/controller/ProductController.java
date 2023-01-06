package com.digital.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.digital.schema.ErrorMsg;
import com.digital.schema.Product;
import com.digital.service.ProductService;
import com.digital.utils.ExceptionUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "상품", description = "Product Related API")
@RequestMapping(value = "/rest/product")

public class ProductController {

	@Resource
	ProductService productSvc;

	@RequestMapping(value = "/inquiry/{keyword}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "상품 검색", notes = "상품명으로 등록 여부를 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Product.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> search(@PathVariable String keyword) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		Product productRes = new Product();

		ErrorMsg errors = new ErrorMsg();

		try {
			productRes = productSvc.searchProduct(keyword);
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		return new ResponseEntity<Product>(productRes, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "상품 등록", notes = "상품 등록을 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Product.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> insert(
			@Parameter(name = "상품 등록", description = "", required = true) @RequestBody Product product) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		Product productRes = new Product();
		try {
			if (productSvc.insertProduct(product)) {
				productRes = productSvc.searchProduct(product.getProductName());
			}

		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		return new ResponseEntity<Product>(productRes, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}
}
