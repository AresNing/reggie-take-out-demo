package com.njk.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njk.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-04-16 10:45
 * @description :
 */

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
