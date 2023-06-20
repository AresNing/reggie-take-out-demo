package com.njk.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njk.reggie.dto.DishDto;
import com.njk.reggie.entity.Dish;

import java.util.List;

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

    // 根据id查询菜品信息和对应的口味信息
    public DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);

    /**
     * 起售/停售套餐
     * @param status
     * @param ids
     */
    public void updateStatusByIds(int status, List<Long> ids);

    /**
     * 删除菜品，同时删除菜品和口味的关联数据
     * @param ids
     */
    public void removeWithFlavor(List<Long> ids);
}
