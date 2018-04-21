package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Description:用户服务
 * @Author:WangYichao
 * @Date:2018/3/25 18:09
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 用户名为空提示
     */
    private final String USERNAME_BLANK = "用户名不能为空";

    /**
     * 用户名存在提示
     */
    private final String USERNAME_EXSIT = "用户名已存在";

    /**
     * 用户名密码错误提示
     */
    private final String USERNAME_PASSWORD_ERROR = "用户名或密码错误";

    /**
     * 电话号码为空提示
     */
    private final String PHONE_BLANK = "电话号码不能为空";

    /**
     * 电话号码存在提示
     */
    private final String PHONE_EXSIT = "电话号码已存在";

    /**
     * 邮箱为空提示
     */
    private final String EMAIL_BLANK = "邮箱不能为空";

    /**
     * 邮箱存在提示
     */
    private final String EMAIL_EXSIT = "邮箱已存在";

    /**
     * 密码为空提示
     */
    private final String PASSWORD_BLANK = "密码不能为空";

    /**
     * token为空提示
     */
    private final String TOKEN_BLANK = "登录信息已过期，请重新登陆";

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;

    /**
     * user 数据访问对象
     */
    @Autowired
    private TbUserMapper tbUserMapper;

    /**
     * jedis 客户端
     */
    @Autowired
    private JedisClient jedisClient;

    @Override
    public TaotaoResult checkUserMessage(String content, Integer type) {

        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();

        switch (type){
            case 1:
                criteria.andUsernameEqualTo(content);
                break;
            case 2:
                criteria.andPhoneEqualTo(content);
                break;
            case 3:
                criteria.andEmailEqualTo(content);
                break;
             default:
                 return TaotaoResult.build(400,"数据类型不存在");

        }

        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);

        if(tbUsers.size()>0){

            return TaotaoResult.ok(false);
        }

        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult addUser(TbUser tbUser) {

        String result = this.checkUserRegisterInfo(tbUser);

        if(result != null){
            return TaotaoResult.build(400,result);
        }

        //密码md5加密
        tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));

        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());

        tbUserMapper.insert(tbUser);

        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult userLogin(TbUser tbUser) {
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(tbUser.getUsername());
        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);
        if(tbUsers.size()==0){
            return TaotaoResult.build(400,USERNAME_PASSWORD_ERROR);
        }

        TbUser user = tbUsers.get(0);
        String password = user.getPassword();
        if(!StringUtils.equals(password,DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()))){
            return TaotaoResult.build(400,USERNAME_PASSWORD_ERROR);
        }

        user.setPassword(null);
        //生成唯一标识
        String token = UUID.randomUUID().toString();
        //验证通过，存入redis中
        jedisClient.set(REDIS_USER_SESSION_KEY+":"+token, JsonUtils.objectToJson(user));
        jedisClient.expire(REDIS_USER_SESSION_KEY+":"+token,SSO_SESSION_EXPIRE);
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {

        if(StringUtils.isBlank(token)){
            return TaotaoResult.build(400,TOKEN_BLANK);
        }

        String userJson = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);

        if(StringUtils.isBlank(userJson)){
            return TaotaoResult.build(400,TOKEN_BLANK);
        }

        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token,SSO_SESSION_EXPIRE);

        TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);

        return TaotaoResult.ok(user);
    }

    /**
     * 检查用户注册信息
     * @param tbUser 用户实体
     * @return 检查结果
     */
    private String checkUserRegisterInfo(TbUser tbUser){

        String result = null;

        result = this.checkUsername(tbUser);

        if(result != null){

            return result;
        }

        result = this.checkPhone(tbUser);

        if(result != null){

            return result;
        }
        result = this.checkEmail(tbUser);

        if(result != null){

            return result;
        }

        result = this.checkPassword(tbUser);

        if(result != null){

            return result;
        }

        return result;
    }

    /**
     * 检查用户名是否合法
     * @param tbUser 用户实体
     * @return 校验结果
     */
    private String checkUsername(TbUser tbUser) {
        String result = null;
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        if(StringUtils.isBlank(tbUser.getUsername())){
            result = USERNAME_BLANK;
            return result;
        }

        criteria.andUsernameEqualTo(tbUser.getUsername());

        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);

        if(tbUsers.size()>0){
            result = USERNAME_EXSIT;
            return result;
        }

        return result;
    }

    /**
     * 检查电话号码是否合法
     * @param tbUser 用户实体
     * @return 校验结果
     */
    private String checkPhone(TbUser tbUser) {
        String result = null;
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        if(StringUtils.isBlank(tbUser.getPhone())){
            result = PHONE_BLANK;
            return result;
        }

        criteria.andPhoneEqualTo(tbUser.getPhone());

        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);

        if(tbUsers.size()>0){
            result = PHONE_EXSIT;
            return result;
        }

        return result;
    }

    /**
     * 检查邮箱是否合法
     * @param tbUser 用户实体
     * @return 校验结果
     */
    private String checkEmail(TbUser tbUser) {
        String result = null;
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        if(StringUtils.isBlank(tbUser.getEmail())){
            result = EMAIL_BLANK;
            return result;
        }

        criteria.andEmailEqualTo(tbUser.getEmail());

        List<TbUser> tbUsers = tbUserMapper.selectByExample(tbUserExample);

        if(tbUsers.size()>0){
            result = EMAIL_EXSIT;
            return result;
        }

        return result;
    }

    /**
     * 检查用户密码是否合法
     * @param tbUser 用户实体
     * @return 校验结果
     */
    private String checkPassword(TbUser tbUser){
        String result = null;

        if(StringUtils.isBlank(tbUser.getPassword())){
            result = PASSWORD_BLANK;
        }

        return result;
    }


}
