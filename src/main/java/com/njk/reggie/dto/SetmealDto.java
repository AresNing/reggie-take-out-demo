package com.njk.reggie.dto;

import com.njk.reggie.entity.Setmeal;
import com.njk.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-05-13 20:38
 * @description :
 */

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;

}
