package com.code.server.mapper;

import com.code.server.pojo.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.code.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zwj
 * @since 2021-08-18
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * 获取所有部门
     * @param parentId
     */
    List<Department> getAllDepartments(Integer parentId);

    /**
     * 添加部门
     * @param department
     * @return
     */
    RespBean addDepartment(Department department);

    /**
     * 删除部门
     * @param department
     */
    void deleteDepartment(Department department);
}
