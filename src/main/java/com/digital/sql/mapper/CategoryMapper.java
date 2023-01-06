package com.digital.sql.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.digital.sql.vo.CategoryVO;

@Mapper
public interface CategoryMapper {

	public CategoryVO getCategory(String categoryName);

	public void setCategory(CategoryVO categoryVO);
}
