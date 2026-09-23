package com.jy.rbac.service;

import com.jy.rbac.pojo.common.PageResult;
import com.jy.rbac.pojo.dto.UserCreateDTO;
import com.jy.rbac.pojo.dto.UserPageQueryDTO;
import com.jy.rbac.pojo.dto.UserUpdateDTO;
import com.jy.rbac.pojo.vo.UserVO;

public interface UserService {
    void add(UserCreateDTO userCreateDTO);

    PageResult<UserVO> getList(UserPageQueryDTO userPageQueryDTO);

    UserVO getUserById(Long id);

    void delete(Long id);

    void update(UserUpdateDTO userUpdateDTO);
}
