package com.jy.rbac.constant;

public class MessageConstant {
    public static final String USER_NOT_FOUND = "用户不存在";
    public static final String USER_NAME_EXISTS = "用户名已存在";
    public static final String EMAIL_EXISTS = "邮箱已存在";
    public static final String  SERVER_ERROR ="服务端异常";
    public static final String DATA_DUPLICATE = "保存失败，该数据已存在（唯一字段重复）";
    public static final String DEPT_NOT_FOUND = "部门不存在";
    public static final String EXIST_CHILD_DEPT= "该部门下存在子部门，无法删除";
    public static final String DEPT_EXIST_USER= "该部门下存在用户，无法删除";
    public static final String DEPT_PARENT_INVALID = "上级部门不能是自己或自己的子部门";
    public static final String ACCOUNT_OR_PASSWORD_ERROR = "账号或密码错误";
    public static final String ACCOUNT_LOGIN_STATUS_FAILED = "登录状态已失效，请重新登录";
    public static final String ACCOUNT_DISABLE = "账号已被禁用";
    public static final String ROLE_NOT_FOUND = "角色不存在";
    public static final String ROLE_NAME_EXISTS = "角色名称已存在";
    public static final String ROLE_KEY_EXISTS = "权限字符已存在";
    public static final String MENU_NOT_FOUND = "菜单不存在";
    public static final String ROLE_IN_USE = "该角色已分配用户，无法删除";
    public static final String MENU_IN_USE = "该菜单已分配给角色，无法删除";
    public static final String NOT_PERMISSION = "无权限访问，请联系管理员";

}
