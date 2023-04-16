package com.njk.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-04-12 10:33
 * @description : 全局异常处理
 */

@ControllerAdvice(annotations = {RestController.class, Controller.class})  // 拦截加了特定注解的控制器
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 异常处理方法
     * @return 返回异常信息
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class) // 指定拦截的异常
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException e) {
        String eMessage = e.getMessage();
        log.error(eMessage);

        if(eMessage.contains("Duplicate entry")) {
            String[] split  = eMessage.split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }

        return R.error("未知错误");
    }

    /**
     * 自定义异常处理方法
     * @return 返回异常信息
     */
    @ExceptionHandler(CustomException.class) // 指定拦截的异常
    public R<String> exceptionHandler(CustomException e) {
        log.error(e.getMessage());
        return R.error(e.getMessage());
    }
}
