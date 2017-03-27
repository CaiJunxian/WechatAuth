package com.wechat.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ResourceLoader {
	@SuppressWarnings("unchecked")
	public static Map<String, Object> loadProperties(String url) throws IOException {

		InputStream input = null;
		
		try {
			input = new FileInputStream(new File(url));
			Yaml yaml = new Yaml();
			return (Map<String, Object>) yaml.load(input);

		} finally {
			if (input != null) {
				input.close();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public static String getPropertyByKeys(Map<String, Object> data, String... keys) {

		if (data == null || keys == null || keys.length == 0) {
			return null;
		}

		int i = 0;

		Map<String, Object> result = null;

		while (i < keys.length - 1) {
			String key = keys[i];
			if (result != null) {
				result = (Map<String, Object>) result.get(key);
			} else {
				result = (Map<String, Object>) data.get(key);
			}
			i++;
		}

		if (result == null) {
			return null;
		}

		Object property = result.get(keys[i]);

		return property == null ? null : property.toString();

	}
}
