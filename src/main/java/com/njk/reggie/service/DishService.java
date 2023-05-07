package com.njk.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njk.reggie.dto.DishDto;
import com.njk.reggie.entity.Dish;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-04-16 10:46
 * @description :
 */

public interface DishService extends IService<Dish> {

    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);
}
