package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:WangYichao
 * @Description:用户服务接口
 * @Date:Created in 2018/3/25 18:14
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 用户服务
     */
    @Autowired
    private UserService userService;


    /**
     * 检查用户信息
     * @param param 需要检查的信息
     * @param type 信息类型
     * @return json结果
     */
    @RequestMapping(value = "/check/{param}/{type}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult checkUserMessage(@PathVariable String param,@PathVariable Integer type){
        try {
            return userService.checkUserMessage(param,type);
        }catch (Exception e){
            return TaotaoResult.build(500,ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 用户注册
     * @param tbUser 用户实体
     * @return json结果
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult userRegister(TbUser tbUser){
        try {
            return userService.addUser(tbUser);
        }catch (Exception e){
            return TaotaoResult.build(500,ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 用户登录
     * @param tbUser 用户实体
     * @return json结果
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult userLogin(TbUser tbUser){
        try {
            return userService.userLogin(tbUser);
        }catch (Exception e){
            return TaotaoResult.build(500,ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * 根据token获取用户信息
     * @param token 登陆标识
     * @return json结果
     */
    @RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback){

        try {
            TaotaoResult result = userService.getUserByToken(token);
            if(StringUtils.isBlank(callback)){

                return result;
            }

            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);

            return mappingJacksonValue;

        }catch (Exception e){
            return TaotaoResult.build(500, ExceptionUtils.getStackTrace(e));
        }
    }
}
