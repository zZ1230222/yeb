package com.code.server.controller;


import com.code.server.pojo.JobLevel;
import com.code.server.pojo.Position;
import com.code.server.pojo.RespBean;
import com.code.server.service.IJobLevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zwj
 * @since 2021-08-18
 */
@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {

    @Autowired
    private IJobLevelService jobLevelService;

    @ApiOperation(value = "获取所有职级信息")
    @GetMapping("/")
    public List<JobLevel> getAllPositions() {
        return jobLevelService.list();
    }

    @ApiOperation(value = "添加职级信息")
    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody JobLevel jobLevel) {
        jobLevel.setCreateDate(LocalDateTime.now());
        if (jobLevelService.save(jobLevel)) {
            return RespBean.success("添加成功!");
        }
        return RespBean.error("添加失败!");
    }

    @ApiOperation(value = "更新职级信息")
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody JobLevel joblevel) {
        if (jobLevelService.updateById(joblevel)) {
            return RespBean.success("更新成功!");
        }
        return RespBean.error("更新失败！");
    }

    @ApiOperation(value = "删除职级信息")
    @DeleteMapping("/{id}")
    public RespBean deleteJobLevel(@PathVariable Integer id) {
        if (jobLevelService.removeById(id)) {
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

    @ApiOperation(value = "批量删除职级信息")
    @DeleteMapping("/")
    public RespBean deleteJobLevelByIds(Integer[] ids) {
        if (jobLevelService.removeByIds(Arrays.asList(ids))) {
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败!");
    }

}
