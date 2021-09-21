package com.code.server.service;

import com.code.server.pojo.Department;
import com.baomidou.mybatisplus.extension.service.IService;
import com.code.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zwj
 * @since 2021-08-18
 */
public interface IDepartmentService extends IService<Department> {

    /**
     * 获取所有部门
     */
    List<Department> getAllDepartments();

    /**
     * 添加部门
     * @param department
     * @return
     */
    RespBean addDepartment(Department department);

    /**
     * 删除部门
     * @param id
     * @return
     */
    RespBean deleteDepartment(Integer id);
}
