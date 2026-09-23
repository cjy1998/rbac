package com.jy.rbac.service;

import com.jy.rbac.pojo.dto.LoginDTO;
import com.jy.rbac.pojo.vo.LoginVO;

public interface AuthService {
    LoginVO login(LoginDTO loginDTO);
}
