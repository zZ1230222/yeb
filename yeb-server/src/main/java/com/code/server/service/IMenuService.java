package com.code.server.service;

import com.code.server.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zwj
 * @since 2021-08-18
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getMenusByAdminId();
}
