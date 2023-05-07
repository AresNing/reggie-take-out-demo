package com.njk.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njk.reggie.common.R;
import com.njk.reggie.entity.Category;
import com.njk.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-04-15 23:37
 * @description : 分类管理
 */

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category 分类信息
     * @return 新增结果
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("新增分类: {}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分页查询
     * @param page 页码
     * @param pageSize 每页显示的条数
     * @return 分页数据
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        log.info("page = {}, pageSize = {}", page, pageSize);

        // 构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        // 添加排序条件，根据sort排序
        wrapper.orderByAsc(Category::getSort);

        // 执行查询
        categoryService.page(pageInfo, wrapper);

        return R.success(pageInfo);

    }

    /**
     * 删除指定分类信息
     * @param ids 分类信息id
     * @return 删除结果
     */
    @DeleteMapping
    public R<String> deleteById(Long ids) {
        log.info("删除分类，id为：{}", ids);

//        categoryService.removeById(ids);
        categoryService.remove(ids);

        return R.success("删除分类成功");
    }

    /**
     * 根据id修改分类信息
     * @param category 分类信息
     * @return 修改结果
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改分类，该分类为：{}", category);
        categoryService.updateById(category);
        return R.success("修改分类成功");
    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        // 构造条件构造器
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        // 根据Type显示
        wrapper.eq(category.getType() != null, Category::getType, category.getType());
        // 根据sort升序，更改时间降序
        wrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        // 执行查询
        List<Category> categoryList = categoryService.list(wrapper);

        return R.success(categoryList);
    }
}
