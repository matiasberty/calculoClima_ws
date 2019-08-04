package com.calcular.clima.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("*")
public class CORSFilter implements Filter {

	public static String origin;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.addHeader("Access-Control-Allow-Origin", origin);
		httpResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		httpResponse.addHeader("Access-Control-Max-Age", "-1");
		httpResponse.addHeader("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type, Accept");
		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.origin = "*";
	}

}