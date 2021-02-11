package com.elinext.crypto.web;

public interface Navigation {

	String[] STATIC_RESOURCES = {"/img/**", "/css/**", "/js/**"};

	String ROOT = "/";
	String INDEX = "/index";
	String INDEX_HTML = "index.html";
	String VIEW = "/view";

	String WEB_SOCKET_ALL_PATTERN = "/ws/**";
	String APPEARED = "/appeared";
	String REMOVED = "/removed";
	String SELL = "/sell";
	String BUY = "/buy";
}
