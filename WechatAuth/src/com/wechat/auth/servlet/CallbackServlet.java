/**
 * 
 */
package com.wechat.auth.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.wechat.auth.util.AuthUtil;

/**
 * @author sysu_
 *
 */
@WebServlet("/callback")
public class CallbackServlet extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(CallbackServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String code = req.getParameter("code");
		logger.info("code: {}", code);
		
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token"
				+ "?appid=" + AuthUtil.APPID
				+ "&secret=" + AuthUtil.APPSECRET
				+ "&code=" + code
				+ "&grant_type=authorization_code";
		logger.debug("url: {}", url);
		
		// get access_token by code
		JSONObject jsonObject = AuthUtil.doGetJson(url);
		
		String openid = jsonObject.getString("openid");
		String accessToken = jsonObject.getString("access_token");
		
		String infoUrl = "https://api.weixin.qq.com/sns/userinfo"
				+ "?access_token=" + accessToken
				+ "&openid=" + openid
				+ "&lang=zh_CN";
		
		logger.info("infoUrl: {}", infoUrl);
		
		// get user's information by access_token
		JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
		
		logger.info("userInfo: {}", userInfo.toString());
	}

}
