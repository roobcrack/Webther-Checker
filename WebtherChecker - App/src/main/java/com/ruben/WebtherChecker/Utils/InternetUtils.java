package com.ruben.WebtherChecker.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class InternetUtils {
	
	public static String readUrl(String web) {
		try {
			@SuppressWarnings("deprecation")
			URL url = new URL(web);
			URLConnection uc = url.openConnection();
			uc.setRequestProperty("User-Agent", "PostmanRuntime/7.20.1");
			uc.connect();
			@SuppressWarnings("resource")
			String lines = new BufferedReader(new InputStreamReader(uc.getInputStream(), StandardCharsets.UTF_8))
				.lines().collect(Collectors.joining());
			return lines;
		} catch (Exception e) {
			System.out.println("Couldn't read the URLL: " + web);
			System.out.println(e.getStackTrace() + " " + e.getCause());
		}
		return null;
	}
}
