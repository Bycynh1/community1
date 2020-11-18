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
//        System.out.println("JSON.toJSONString======" + JSON.toJSONString(accessTokenDTO));
        RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO), mediaType);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
//            System.out.println("string===============" + string);
            String token = string.split("&")[0].split("=")[1];
            System.out.println("token================" + token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public GithubUser getUser(String accessToken) {
        //Authorization: token OAUTH-TOKEN
        //GET https://api.github.com/user

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

