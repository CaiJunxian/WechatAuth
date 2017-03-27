package com.wechat.auth.util;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wechat.common.util.ResourceLoader;


public class AuthUtil {

	private static final Logger logger = LoggerFactory.getLogger(AuthUtil.class);
	private static Map<String, Object> data = null;
	public static String APPID = "";
	public static String APPSECRET = "";
	public static String DOMAIN = "";

	static {
		String url = AuthUtil.class.getClassLoader().getResource("").getPath().replace("/build/classes", "").replace("%20", "") + "../application-dev.yml";
		try {
			data = ResourceLoader.loadProperties(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		APPID = ResourceLoader.getPropertyByKeys(data, "wechat", "appId");
		APPSECRET = ResourceLoader.getPropertyByKeys(data, "wechat", "appSecret");
		DOMAIN = ResourceLoader.getPropertyByKeys(data, "wechat", "domain");
		logger.info("appid: {}, appsecret: {}, domain: {}", APPID, APPSECRET, DOMAIN);
	}



	public static JSONObject doGetJson(String url) throws ParseException, IOException {
		JSONObject jsonObject = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			CloseableHttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result;
				try {
					result = EntityUtils.toString(entity, "UTF-8");
				} finally {
					if (response != null) {
						response.close();
					}
				}
				jsonObject = JSON.parseObject(result);
			}
		} finally {
			httpclient.close();
		}

		return jsonObject;
	}

}
