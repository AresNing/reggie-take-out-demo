package com.njk.reggie.common;/**
* Created with Intellij IDEA
* <h3>reggie_take_out_demo<h3>
*
* @author : AresNing
* @date : 2023-04-13 21:36
* @description : 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
*/
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
