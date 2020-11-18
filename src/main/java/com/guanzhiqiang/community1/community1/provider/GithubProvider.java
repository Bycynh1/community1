package com.guanzhiqiang.community1.community1.provider;

import com.alibaba.fastjson.JSON;
import com.guanzhiqiang.community1.community1.dto.AccessTokenDTO;
import com.guanzhiqiang.community1.community1.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;


/**
 * @author gzq
 * @date 2020/11/12
 **/

@Component
public class GithubProvider {
    public static final MediaType mediaType = MediaType.get("application/json; charset=utf-8");

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {

        OkHttpClient client = new OkHttpClient();
        //JSON.toJSONString() 这个要做什么  把这个DTO转化成
        System.out.println("JSON.toJSONString======" + JSON.toJSONString(accessTokenDTO));
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);//这块？？对 就是 是不是获取到值了 但是回来解析的不对 accessTokenDTO解析有问题？ 你说的这个对是这个意思
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println("string===============" + string);//这个是？？？？我想打印一下这个string  因为这个返回的是一个字符串 带着token 啊 我给你看一下官方给的文档
            String token = string.split("&")[0].split("=")[1];
            System.out.println("token================" + token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Authorization: token OAUTH-TOKEN
    //GET https://api.github.com/user

    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        final String credential = Credentials.basic("access_token",accessToken);
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization",credential)
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

