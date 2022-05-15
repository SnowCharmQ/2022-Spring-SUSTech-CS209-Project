package com.cs209.project.script;

import  org.apache.commons.httpclient.Cookie;
import  org.apache.commons.httpclient.HttpClient;
import  org.apache.commons.httpclient.NameValuePair;
import  org.apache.commons.httpclient.cookie.CookiePolicy;
import  org.apache.commons.httpclient.methods.GetMethod;
import  org.apache.commons.httpclient.methods.PostMethod;

class  HttpLogin {

    public  static  void  main(String[] args) {
        // 登陆 Url
        String loginUrl =  "https://stackoverflow.com/users/login?ssrc=head&returnurl=https%3a%2f%2fstackoverflow.com%2f" ;
        // 需登陆后访问的 Url
        String dataUrl =  "https://api.stackexchange.com/2.3/tags/spring-boot/faq?page=25&pagesize=100&site=stackoverflow" ;
        HttpClient httpClient =  new  HttpClient();

        // 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
        PostMethod postMethod =  new  PostMethod(loginUrl);

        // 设置登陆时要求的信息，用户名和密码
        NameValuePair[] data = {  new  NameValuePair( "loginName" ,  "12012211@mail.sustech.edu.cn" ),  new  NameValuePair( "loginPasswd" ,  "chenqiujiang0904" ) };
        postMethod.setRequestBody(data);
        try  {
            // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            int  statusCode=httpClient.executeMethod(postMethod);

            // 获得登陆后的 Cookie
            Cookie[] cookies = httpClient.getState().getCookies();
            StringBuilder tmpcookies =  new StringBuilder();
            for  (Cookie c : cookies) {
                tmpcookies.append(c.toString()).append(";");
                System.out.println( "cookies = " + c);
            }
//            if (statusCode== 302 ){ //重定向到新的URL
                System.out.println( "success" );
                // 进行登陆后的操作
                GetMethod getMethod =  new  GetMethod(dataUrl);
                // 每次访问需授权的网址时需带上前面的 cookie 作为通行证
                getMethod.setRequestHeader( "cookie" , tmpcookies.toString());
                // 你还可以通过 PostMethod/GetMethod 设置更多的请求后数据
                // 例如，referer 从哪里来的，UA 像搜索引擎都会表名自己是谁，无良搜索引擎除外
                postMethod.setRequestHeader( "Referer" ,  "https://stackoverflow.com/users/login?ssrc=head&returnurl=https%3a%2f%2fstackoverflow.com%2f" );
                postMethod.setRequestHeader( "User-Agent" ,  "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36" );
                httpClient.executeMethod(getMethod);
                // 打印出返回数据，检验一下是否成功
                System.out.println(httpClient.getState());
                String text = getMethod.getResponseBodyAsString();
                System.out.println(text);
//            }
//            else  {
//                System.out.println( "登录失败" );
//            }
        }
        catch  (Exception e) {
            e.printStackTrace();
        }
    }
}