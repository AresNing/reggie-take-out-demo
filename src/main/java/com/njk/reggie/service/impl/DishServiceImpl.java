package com.njk.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njk.reggie.common.CustomException;
import com.njk.reggie.dto.DishDto;
import com.njk.reggie.entity.Dish;
import com.njk.reggie.entity.DishFlavor;
import com.njk.reggie.entity.Setmeal;
import com.njk.reggie.entity.SetmealDish;
import com.njk.reggie.mapper.DishMapper;
import com.njk.reggie.service.DishFlavorService;
import com.njk.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-04-16 10:48
 * @description :
 */

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品，同时保存对应的口味数据
     * @param dishDto
     */
    @Transactional  // 多表查询要开启事务
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long dishId = dishDto.getId(); //菜品id

        // 菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map(item -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        // 保存菜品口味数据到菜品口味表dish_flavor
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties(dish, dishDto);

        // 查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(wrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        // 更新 dish 表基本信息
        this.updateById(dishDto);

        //清理当前菜品对应口味数据 --- dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(wrapper);

        //添加当前提交过来的口味数据 --- dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((flavor) -> {
            flavor.setDishId(dishDto.getId());
            return flavor;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public void updateStatusByIds(int status, List<Long> ids) {
        // 更新dish
        // update dish set status = status where id in (1, 2, 3);
        List<Dish> dishes = this.listByIds(ids);
        dishes = dishes.stream().map(dish -> {
            dish.setStatus(status);
            return dish;
        }).collect(Collectors.toList());
        this.updateBatchById(dishes);
    }

    /**
     * 删除菜品，同时需要删除菜品和口味的关联数据
     * @param ids
     */
    @Transactional
    public void removeWithFlavor(List<Long> ids) {
        // 查询菜品状态，确定是否可以删除
        // select count(*) from dish where id in (1, 2, 3) and status = 1;
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Dish::getId, ids);
        wrapper.eq(Dish::getStatus, 1);
        int count = this.count(wrapper);
        if(count > 0) {
            // 如果不能删除，抛出业务异常
            throw new CustomException("菜品正在售卖中，不可以删除");
        }

        // 如果可以删除，先删除套餐表中的数据 -- dish
        this.removeByIds(ids);

        // 删除关系表中的数据 -- dish_flavor
        // delete from dish_flavor where dish_id in (1, 2, 3);
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(DishFlavor::getDishId, ids);
        dishFlavorService.remove(lambdaQueryWrapper);
    }
}
