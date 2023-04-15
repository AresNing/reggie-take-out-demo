package com.njk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njk.reggie.entity.Category;
import com.njk.reggie.mapper.CategoryMapper;
import com.njk.reggie.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-04-15 23:36
 * @description :
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
