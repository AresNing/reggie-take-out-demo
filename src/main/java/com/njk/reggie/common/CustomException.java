package com.njk.reggie.common;

/**
 * Created with Intellij IDEA
 * <h3>reggie_take_out_demo<h3>
 *
 * @author : AresNing
 * @date : 2023-04-16 12:38
 * @description : 自定义异常
 */
public class CustomException extends RuntimeException {
    public CustomException(String msg) {
        super(msg);
    }
}
