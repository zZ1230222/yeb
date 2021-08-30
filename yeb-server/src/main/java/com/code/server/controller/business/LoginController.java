package com.code.server.controller.business;

import com.code.server.pojo.Admin;
import com.code.server.pojo.RespBean;
import com.code.server.pojo.vo.AdminLoginVo;
import com.code.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

/**
 * 登录
 */

@Api(tags = "LoginController")
@RestController
public class LoginController {
    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录之后返回Token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginVo adminLoginVo, HttpServletRequest request) {
        return adminService.login(adminLoginVo.getUsername(), adminLoginVo.getPassword(), request);
    }

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal){
        if (Objects.isNull(principal)){
            return null;
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        admin.setPassword(null);
        return admin;
    }

    @ApiOperation(value = "退出登录")
    @GetMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功!");
    }

}
