package com.njk.reggie.controller;

import com.njk.reggie.common.R;
import com.njk.reggie.entity.Category;
import com.njk.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("新增分类: {}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }
}
