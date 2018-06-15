package com.biboheart.huip.user.security.provider;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.biboheart.brick.utils.CheckUtils;
import com.biboheart.huip.user.domain.Account;
import com.biboheart.huip.user.domain.User;
import com.biboheart.huip.user.service.AccountService;
import com.biboheart.huip.user.service.UserService;

public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println(this.getClass().getName() + " authenticate");
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		String password = (String) authentication.getCredentials();
		if (CheckUtils.isEmpty(password)) {
			throw new BadCredentialsException("密码不能为空");
		}
		Account account = accountService.load(null, username, null);
		if (null == account) {
			throw new BadCredentialsException("用户不存在");
		}
		User user = userService.load(account.getUid(), null);
		if (null == user) {
			throw new BadCredentialsException("用户不存在");
		}
		if (password.length() != 32) {
			password = DigestUtils.md5Hex(password);
		}
		if (!password.equals(account.getPassword())) {
			throw new BadCredentialsException("用户名或密码不正确");
		}
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(username, password, listUserGrantedAuthorities(user.getId()));
		result.setDetails(authentication.getDetails());
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	private Set<GrantedAuthority> listUserGrantedAuthorities(Long uid) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		if (CheckUtils.isEmpty(uid)) {
			return authorities;
		}
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return authorities;
	}

}
