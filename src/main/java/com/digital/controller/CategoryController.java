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

import com.digital.schema.Category;
import com.digital.schema.ErrorMsg;
import com.digital.service.CategoryService;
import com.digital.utils.ExceptionUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "카테고리", description = "Category Related API")
@RequestMapping(value = "/rest/category")

public class CategoryController {

	@Resource
	CategoryService categorySvc;

	@RequestMapping(value = "/inquiry/{keyword}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "카테고리 검색", notes = "카테고리 등록 여부를 검색하는 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Category.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })
	public ResponseEntity<?> productList(@PathVariable String keyword) {

		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		Category category_res = new Category();
		ErrorMsg errors = new ErrorMsg();

		try {
			category_res = categorySvc.searchCategory(keyword);
		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}
		return new ResponseEntity<Category>(category_res, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}

	@RequestMapping(value = "/insert", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "카테고리 등록", notes = "카테고리 등록을 위한 API.")
	@ApiResponses({ @ApiResponse(code = 200, message = "성공", response = Category.class),
			@ApiResponse(code = 500, message = "실패", response = ErrorMsg.class) })

	public ResponseEntity<?> categoryWrite(
			@Parameter(name = "상품카테고리 등록", required = true) @RequestBody Category category) {
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		ErrorMsg errors = new ErrorMsg();
		Category category_res = new Category();
		try {
			if (categorySvc.insertCategory(category)) {
				category_res = categorySvc.searchCategory(category.getCategoryName());
			}

		} catch (Exception e) {
			return ExceptionUtils.setException(errors, 500, e.getMessage(), header);
		}

		return new ResponseEntity<Category>(category_res, header, HttpStatus.valueOf(200)); // ResponseEntity를 활용한 응답 생성
	}

}
