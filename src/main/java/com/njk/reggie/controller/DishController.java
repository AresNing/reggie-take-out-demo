package com.njk.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njk.reggie.common.R;
import com.njk.reggie.dto.DishDto;
import com.njk.reggie.entity.Category;
import com.njk.reggie.entity.Dish;
import com.njk.reggie.service.CategoryService;
import com.njk.reggie.service.DishFlavorService;
import com.njk.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-04-27 19:15
 * @description :
 */

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * 菜品信息分页查询
     * @param page 页码
     * @param pageSize 每页显示的条数
     * @param name 菜品名称
     * @return 分页数据
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        // 构造分页构造器
        Page<Dish> pageInfo = new Page(page, pageSize);
        Page<DishDto> dishDtoPage = new Page();

        // 构造条件构造器
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        // 根据过滤条件，按照name模糊查询
        wrapper.like(name != null, Dish::getName, name);
        // 添加排序条件，根据sort和update_time排序
        wrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        // 执行分页查询
        dishService.page(pageInfo, wrapper);

        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> dishDtoList = records.stream().map((dish) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(dish, dishDto);

            Long categoryId = dish.getCategoryId();  // 分类id
            // 根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(dishDtoList);

        return R.success(dishDtoPage);
    }
}
