package com.njk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njk.reggie.entity.Dish;
import com.njk.reggie.mapper.DishMapper;
import com.njk.reggie.service.DishService;
import org.springframework.stereotype.Service;

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
}
