package com.code.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.code.server.config.security.component.JwtTokenUtil;
import com.code.server.mapper.AdminRoleMapper;
import com.code.server.mapper.RoleMapper;
import com.code.server.pojo.Admin;
import com.code.server.mapper.AdminMapper;
import com.code.server.pojo.AdminRole;
import com.code.server.pojo.RespBean;
import com.code.server.pojo.Role;
import com.code.server.service.IAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.code.server.utils.AdminUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zwj
 * @since 2021-08-18
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 操作员登陆
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        String captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)) {
            return RespBean.error("验证码填写错误！");
        }
        if (Objects.isNull(userDetails) || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return RespBean.error("用户名或密码错误!");
        }
        if (!userDetails.isEnabled()) {
            return RespBean.error("账号被禁用，请联系管理员!");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String token = jwtTokenUtil.generatorToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return RespBean.success("登录成功!", tokenMap);
    }

    /**
     * 通过用户名获取操作员信息
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username", username).eq("enabled", true));
    }

    /**
     * 获取登录员角色
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

    /**
     * 根据关键词获取所有操作员
     * @param keywords
     * @return
     */
    @Override
    public List<Admin> getAllAdmins(String keywords) {
        return adminMapper.getAllAdmins(AdminUtil.getCurrentAdmin().getId(), keywords);
    }

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    @Override
    @Transactional
    public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
        //删除操作员角色
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", adminId));
        //添加新的操作员角色
        Integer result = adminRoleMapper.addAdminRole(adminId, rids);
        if (rids.length == result){
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
}
