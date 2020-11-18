package com.guanzhiqiang.community1.community1.controller;

import com.guanzhiqiang.community1.community1.dto.AccessTokenDTO;
import com.guanzhiqiang.community1.community1.dto.GithubUser;
import com.guanzhiqiang.community1.community1.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * @author gzq
 * @date 2020/11/12
 **/

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request  //
    ) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
//        System.out.println("accessTokenDTO====" +accessTokenDTO.toString());
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
//        System.out.println("accessToken===" + accessToken);
        GithubUser user = githubProvider.getUser(accessToken);
//        System.out.println("user======" +user.toString());
        System.out.println(user.getName());
        if (user != null) {
            //登录成功
            request.getSession().setAttribute("user", user);
            return "redirect:/";
        } else {
            //登录失败，重新登录
            return "redirect:/";
        }
    }
}
