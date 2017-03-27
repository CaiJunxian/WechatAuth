/**
 * 
 */
package com.wechat.auth.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wechat.auth.util.AuthUtil;

/**
 * @author sysu_
 *
 */

@WebServlet("/wechatLogin")
public class LoginServlet extends HttpServlet {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String callBackUrl = AuthUtil.DOMAIN + "/WechatAuth/callback";
		
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize"
				+ "?appid=" + AuthUtil.APPID
				+ "&redirect_uri=" + URLEncoder.encode(callBackUrl, "UTF-8")
				+ "&response_type=code"
				+ "&scope=snsapi_userinfo"
				+ "&state=STATE#wechat_redirect";
		
		logger.debug("url: {}", url);
		
		resp.sendRedirect(url);
	}
}
