package com.jy.rbac.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.jy.rbac.constant.MessageConstant;
import com.jy.rbac.constant.StatusConstant;
import com.jy.rbac.exception.BaseException;
import com.jy.rbac.mapper.UserMapper;
import com.jy.rbac.pojo.dto.LoginDTO;
import com.jy.rbac.pojo.entity.User;
import com.jy.rbac.pojo.vo.LoginVO;
import com.jy.rbac.pojo.vo.UserVO;
import com.jy.rbac.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        User user = userMapper.getUserByUserName(loginDTO.getUsername());
        if (user == null) {
            throw new BaseException(MessageConstant.ACCOUNT_OR_PASSWORD_ERROR);
        }

        String password =  DigestUtils.md5DigestAsHex(loginDTO.getPassword().getBytes());
        if(!user.getPassword().equals(password)){
            throw new BaseException(MessageConstant.ACCOUNT_OR_PASSWORD_ERROR);
        }

        if (StatusConstant.USER_ACCOUNT_DISABLE_STATUS.equals(user.getStatus())) {
            throw new BaseException(MessageConstant.ACCOUNT_DISABLE);
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);
        LoginVO loginVO = new LoginVO();
        loginVO.setUser(userVO);

        StpUtil.login(user.getUserId());
        StpUtil.getSession().set("userName",user.getUserName());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        loginVO.setToken(tokenInfo.getTokenValue());
        return loginVO;
    }
}
