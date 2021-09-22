package com.code.server.mapper;

import com.code.server.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.server.pojo.RespBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zwj
 * @since 2021-08-18
 */
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 根据关键词获取所有操作员，同时排除当前操作员
     * @param id
     * @param keywords
     * @return
     */
    List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keywords") String keywords);

}
