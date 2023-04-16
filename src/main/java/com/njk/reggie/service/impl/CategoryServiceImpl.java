package com.njk.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njk.reggie.common.CustomException;
import com.njk.reggie.entity.Category;
import com.njk.reggie.entity.Dish;
import com.njk.reggie.entity.Setmeal;
import com.njk.reggie.mapper.CategoryMapper;
import com.njk.reggie.service.CategoryService;
import com.njk.reggie.service.DishService;
import com.njk.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-04-15 23:36
 * @description :
 */

@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类信息，删除之前进行判断是否关联菜品或套餐
     * @param id 分类信息id
     */
    @Override
    public void remove(Long id) {
        // 判断是否关联菜品
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if(count1 > 0) {
            // 说明该分类与菜品关联，抛出自定义异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        // 判断是否关联套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0) {
            // 说明该分类与套餐关联，抛出自定义异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }

        // 没有与任何菜品或套餐关联，正常删除
        super.removeById(id);
    }
}
