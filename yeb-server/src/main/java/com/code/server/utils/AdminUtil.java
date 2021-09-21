package com.code.server.utils;

import com.code.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

public class AdminUtil {

    /**
     * 获取当前登陆操作员
     * @return
     */
    public static Admin getCurrentAdmin(){
        return ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

}
