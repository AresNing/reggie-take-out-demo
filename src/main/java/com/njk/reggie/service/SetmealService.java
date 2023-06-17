package com.njk.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.njk.reggie.dto.DishDto;
import com.njk.reggie.dto.SetmealDto;
import com.njk.reggie.entity.Setmeal;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-04-16 10:47
 * @description :
 */

public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐，同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * 根据id查询套餐信息及其菜品信息
     * @param id
     * @return
     */
    public SetmealDto getByIdWithDish(Long id);

    /**
     * 更新套餐信息，同时更新对应的菜品信息
     * @param setmealDto
     */
    public void updateWithDish(SetmealDto setmealDto);
}
