package com.picc.chexian.core.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class IpUtil {

	public static String ip_regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";  
	public static Pattern ip_pattern = Pattern.compile(ip_regex);  

	public static String inner_regex = "(10|172|192)\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})\\.([0-1][0-9]{0,2}|[2][0-5]{0,2}|[3-9][0-9]{0,1})";
	public static Pattern inner_pattern = Pattern.compile(inner_regex);

	public static String illegal_prefix = "169.254";  
	
	public static boolean isOuterIP(String ip){
		if (StringUtils.isBlank(ip)){
			return false;
		}
		if (ip_pattern.matcher(ip).matches()){
			if (ip.startsWith(illegal_prefix) || inner_pattern.matcher(ip).matches()){
				return false;
			}
			return true;
		}
		return false;
	}
	
	public static void main(String[] args){
		String ip = "12.5.8.8";
		System.out.println(isOuterIP(ip));
	}

}
