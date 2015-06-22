package com.CommonData;

public class StringData {
	private final String LoginPath = "http://192.168.191.1:8080/NoticeServer/servlet/UserLoginServlet";
	private final static String RegisterPath = "http://192.168.191.1:8080/NoticeServer/servlet/RegisterServlet";
	private final String GetNoticePath = "http://192.168.191.1:8080/NoticeServer/servlet/NoticeServlet";
	private final String XMLPath = "http://192.168.191.1:8080/NoticeServer/Notice.xml";
	private final String LocalXMLPath = "/sdcard/notices.xml";

	public String getXMLPath() {
		return XMLPath;
	}

	public static String getRegisterPath() {
		return RegisterPath;
	}

	public String getGetNoticePath() {
		return GetNoticePath;
	}

	public String getLoginPath() {
		return LoginPath;
	}

	public String getLocalXMLPath() {
		return LocalXMLPath;
	}

}
