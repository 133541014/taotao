package com.taotao.controller;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:WangYichao
 * @Description:登陆验证控制器
 * @Date:Created in 2018/1/21 19:16
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String Login(HttpServletRequest request, Model model) throws Exception {

        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");

        if (exceptionClassName != null) {
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {

                model.addAttribute("error","账号不存在");

            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {

                model.addAttribute("error","密码错误");

            } else {
                model.addAttribute("error","未知错误");
            }
        }
        //登陆失败还到login页面
        return "login";
    }

}
