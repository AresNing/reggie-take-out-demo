package com.njk.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.njk.reggie.service.EmployeeService;
import com.njk.reggie.entity.Employee;
import com.njk.reggie.mapper.EmployeeMapper;
import org.springframework.stereotype.Service;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-04-11 15:58
 * @description :
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
