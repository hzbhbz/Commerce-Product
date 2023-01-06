package com.digital.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.digital.schema.Category;
import com.digital.sql.mapper.CategoryMapper;
import com.digital.sql.vo.CategoryVO;

@Component
public class CategoryService {

	@Resource
	CategoryMapper categoryMapper;

	// 카테고리 검색
	public Category searchCategory(String categoryName) throws Exception {

		CategoryVO categoryVO = categoryMapper.getCategory(categoryName);
		Category category = new Category();

		if (categoryVO != null) {
			category.setCategoryId(categoryVO.getCategoryId());
			category.setCategoryName(categoryVO.getCategoryName());
		}
		return category;
	}

	// 카테고리 등록
	public boolean insertCategory(Category category) throws Exception {

		CategoryVO categoryVO = new CategoryVO();

		try {
			// 카테고리 중복 여부
			if (categoryMapper.getCategory(category.getCategoryName()) != null) {
				throw new Exception("등록된 카테고리입니다.");
			}

			category.setCategoryId(System.currentTimeMillis());

			categoryVO.setCategoryId(category.getCategoryId());
			categoryVO.setCategoryName(category.getCategoryName());

			categoryMapper.setCategory(categoryVO);

			return true;

		} catch (Exception e) {
			throw e;
		}
	}
}
