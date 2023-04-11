package com.njk.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.njk.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-04-11 15:50
 * @description :
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
