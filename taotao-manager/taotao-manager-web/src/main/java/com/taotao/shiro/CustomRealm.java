package com.taotao.shiro;

import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author:WangYichao
 * @Description:
 * @Date:Created in 2018/1/21 19:12
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private TbUserMapper userMapper;

    /**
     * @Description:认证方法
     * @Author:WangYichao
     * @Date:2018/1/21 19:14
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        //查询对应用户是否存在
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> users = userMapper.selectByExample(userExample);
        if(users.size()==0){
            //没有查询到用户
            return null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(users.get(0),users.get(0).getPassword(),getName());

        return simpleAuthenticationInfo;
    }

    /**
     * @Description:授权方法
     * @Author:WangYichao
     * @Date:2018/1/21 19:14
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


}
