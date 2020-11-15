package com.guanzhiqiang.community1.community1.controller;

import com.guanzhiqiang.community1.community1.dto.AccessTokenDTO;
import com.guanzhiqiang.community1.community1.dto.GithubUser;
import com.guanzhiqiang.community1.community1.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * @author gzq
 * @date 2020/11/12
 **/

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("19fa57cea0d4660afb5d");
        accessTokenDTO.setClient_secret("0f2c75332b7e2b129dabe5b082ff3ccaefc7ebbb");
        System.out.println("accessTokenDTO====" +accessTokenDTO.toString());
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println("accessToken===" + accessToken);
        GithubUser user = githubProvider.getUser(accessToken);//这里你确定是用accessToken来获取用户信息？？？
        System.out.println("user======" +user.toString());
        System.out.println(user.getName());
        return "index";
    }
}
