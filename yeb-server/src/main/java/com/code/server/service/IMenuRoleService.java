package com.code.server.service;

import com.code.server.pojo.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.code.server.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zwj
 * @since 2021-08-18
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     */
    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
