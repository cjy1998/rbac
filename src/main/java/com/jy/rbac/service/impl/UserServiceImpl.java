package com.jy.rbac.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jy.rbac.constant.MessageConstant;
import com.jy.rbac.constant.PasswordConstant;
import com.jy.rbac.exception.BaseException;
import com.jy.rbac.mapper.RoleMapper;
import com.jy.rbac.mapper.UserMapper;
import com.jy.rbac.mapper.UserRoleMapper;
import com.jy.rbac.pojo.common.PageResult;
import com.jy.rbac.pojo.dto.UserCreateDTO;
import com.jy.rbac.pojo.dto.UserPageQueryDTO;
import com.jy.rbac.pojo.dto.UserUpdateDTO;
import com.jy.rbac.pojo.entity.User;
import com.jy.rbac.pojo.vo.UserVO;
import com.jy.rbac.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(UserCreateDTO userCreateDTO) {
        checkUnique(userCreateDTO.getUserName(), userCreateDTO.getEmail(), 0L);
        User user = new User();
        BeanUtils.copyProperties(userCreateDTO,user);
        String password = user.getPassword();
        if(password !=null && !password.isBlank()){
            user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        }else {
            user.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        }
        userMapper.add(user);

        /**
         * 关联角色
         */
        List<Long> roleIds = userCreateDTO.getRoleIds();
        if(roleIds != null && !roleIds.isEmpty()){
            checkRoleIds(roleIds);
            userRoleMapper.insertBatch(user.getUserId(),roleIds);
        }
    }

    @Override
    public PageResult<UserVO> getList(UserPageQueryDTO userPageQueryDTO) {
        PageHelper.startPage(userPageQueryDTO.getPageNum(), userPageQueryDTO.getPageSizeNum());
        Page<User> users = userMapper.getList(userPageQueryDTO);
        List<UserVO> rows = users.getResult().stream()
                .map(user -> {
                    UserVO vo = new UserVO();
                    BeanUtils.copyProperties(user, vo);
                    return vo;
                })
                .toList();
        return new PageResult<UserVO>(users.getTotal(),rows);
    }

    @Override
    public UserVO getUserById(Long id) {
        User user  = userMapper.getUserById(id);
        if(user != null){
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user,userVO);
            List<Long> roleIds = userRoleMapper.selectRoleIdsByUserId(user.getUserId());
            userVO.setRoleIds(roleIds);
            return userVO;
        }
       throw new BaseException(MessageConstant.USER_NOT_FOUND);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        int rows = userMapper.delete(id);
        if (rows == 0){
            throw new BaseException(MessageConstant.USER_NOT_FOUND);
        }
        userRoleMapper.deleteByUserId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateDTO userUpdateDTO) {
        getUserById(userUpdateDTO.getUserId());
        checkUnique(userUpdateDTO.getUserName(), userUpdateDTO.getEmail(), userUpdateDTO.getUserId());
        User user = new User();
        BeanUtils.copyProperties(userUpdateDTO,user);
        int rows =  userMapper.edit(user);
        if (rows == 0){
            throw new BaseException(MessageConstant.USER_NOT_FOUND);
        }

        Long userId = userUpdateDTO.getUserId();
        List<Long> roleIds = userUpdateDTO.getRoleIds();
        if (roleIds != null) {                    // null = 本次不动关联
            userRoleMapper.deleteByUserId(userId);
            if (!roleIds.isEmpty()) {             // 空数组 = 清空后不重插
                checkRoleIds(roleIds);
                userRoleMapper.insertBatch(userId, roleIds);
            }
        }
    }

    /**
     * 校验提交的 roleId 都真实存在（数量对不上说明有假 id 或重复 id）
     */
    private void checkRoleIds(List<Long> roleIds) {
        if (roleMapper.countByIds(roleIds) != roleIds.size()) {
            throw new BaseException(MessageConstant.ROLE_NOT_FOUND);
        }
    }

    private void checkUnique(String userName, String email, Long excludeUserId) {
        if (userMapper.countByUserName(userName, excludeUserId) > 0) {
            throw new BaseException(MessageConstant.USER_NAME_EXISTS);
        }
        if (StringUtils.hasText(email) && userMapper.countByEmail(email, excludeUserId) > 0) {
            throw new BaseException(MessageConstant.EMAIL_EXISTS);
        }
    }
}
