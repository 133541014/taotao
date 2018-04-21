package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * @author:WangYichao
 * @Description:用户Service
 * @Date:Created in 2018/3/25 18:03
 */
public interface UserService {

    /**
     * 检查用户信息是否可用
     * @param content 信息
     * @param type 信息类型
     * @return 校验结果
     */
    TaotaoResult checkUserMessage(String content,Integer type);

    /**
     * 添加用户
     * @param tbUser 用户实体
     * @return 结果
     */
    TaotaoResult addUser(TbUser tbUser);

    /**
     * 用户登录
     * @param tbUser 用户实体
     * @return 校验结果
     */
    TaotaoResult userLogin(TbUser tbUser);

    /**
     * 根据token获取用户信息
     * @param token 用户登录标识
     * @return 校验结果
     */
    TaotaoResult getUserByToken(String token);


}
