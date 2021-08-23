package com.code.server.service;

import com.code.server.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.code.server.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zwj
 * @since 2021-08-18
 */
public interface IAdminService extends IService<Admin> {
    RespBean login(String username, String password, HttpServletRequest request);
    Admin getAdminByUsername(String username);
}
