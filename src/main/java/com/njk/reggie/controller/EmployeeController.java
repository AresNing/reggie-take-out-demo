package com.njk.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.njk.reggie.common.R;
import com.njk.reggie.entity.Employee;
import com.njk.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-04-11 16:05
 * @description :
 */

@Slf4j
@RestController // @RestController = @Controller + @ResponseBody
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request 通过request获取session
     * @param employee 前端传来的员工信息
     * @return 登录成功返回员工信息，失败返回错误信息
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 1. 将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2. 根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(wrapper);

        // 3. 如果没有查询到则返回登录失败结果
        if(emp == null) {
            return R.error("登录失败");
        }

        // 4. 密码比对，如果不一致则返回登录失败结果
        if(!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }

        // 5. 查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }

        // 6. 登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 员工登出
     * @param request 通过request获取session
     * @return 登出成功返回成功信息，失败返回错误信息
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        // 1. 从session中移除员工id
        request.getSession().removeAttribute("employee");
        return R.success("登出成功");
    }

    /**
     * 新增员工
     * @param employee 前端传来的员工信息
     * @return 新增成功返回成功信息，失败返回错误信息
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工: {}", employee.toString());

        // 设置初始密码，123456，需要进行md5加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // 设置创建时间和更新时间
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());

        // 获取当前登录用户id
        //Long empId = (Long) request.getSession().getAttribute("employee");
        // 设置创建人和更新人
        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);

        // 需要对保存操作进行异常捕获，有可能因为重复账号而导致保存失败
        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    /**
     * 员工信息分页查询， 基于MybatisPlus的分页插件
     * @param page 当前页
     * @param pageSize 每页显示条数
     * @param name 员工姓名
     * @return 分页查询结果
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);

        // 构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        // 构造条件构造器
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件，where name like
        wrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        // 添加排序条件，order by updateTime desc
        wrapper.orderByDesc(Employee::getUpdateTime);

        // 执行查询
        employeeService.page(pageInfo, wrapper);

        return R.success(pageInfo);
    }

    /**
     * 修改员工信息
     * @param request 通过request获取session
     * @param employee 前端传来的员工信息
     * @return 修改成功返回成功信息，失败返回错误信息
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("employee: {}", employee);

        // 获取当前登录用户id
        //Long empId = (Long) request.getSession().getAttribute("employee");

        // 设置更新时间和更新人
        //employee.setUpdateUser(empId);
        //employee.setUpdateTime(LocalDateTime.now());

        employeeService.updateById(employee);
        return R.success("修改成功");
    }

    /**
     * 根据id查询员工信息
     * @param id 员工id
     * @return 查询成功返回员工信息，失败返回错误信息
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息: {}", id);
        Employee employee = employeeService.getById(id);
        if(employee == null) {
            return R.error("没有查询到对应的员工信息");
        }
        return R.success(employee);
    }

}
