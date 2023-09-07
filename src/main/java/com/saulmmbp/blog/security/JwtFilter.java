package com.saulmmbp.blog.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	private JwtUtility jwtUtility;
	private UserDetailsService userDetailsService;
	
	public JwtFilter(JwtUtility jwtUtility, UserDetailsService userDetailsService) {
		this.jwtUtility = jwtUtility;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwtToken = getToken(request);
		if (jwtToken != null) {
			String username = jwtUtility.getUsername(jwtToken);
			if (username != null) {
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
	
	private String getToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(StringUtils.hasText(jwtToken) && jwtToken.startsWith("Bearer ")) return jwtToken.replace("Bearer ", "");
		return null;
	}

}
