package com.biboheart.huip.user.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.biboheart.brick.utils.CheckUtils;
import com.biboheart.huip.user.domain.Account;
import com.biboheart.huip.user.domain.User;
import com.biboheart.huip.user.service.AccountService;
import com.biboheart.huip.user.service.UserService;

@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if(CheckUtils.isEmpty(username)) {
			throw new BadCredentialsException("用户名不能为空");
		}
		Account account = accountService.load(null, username, username);
		if (null == account) {
			throw new BadCredentialsException("用户不存在");
		}
		User user = userService.load(account.getUid(), null);
		if(null == user) {
			throw new BadCredentialsException("用户不存在");
		}
		return new org.springframework.security.core.userdetails.User(username, account.getPassword(), true, true, true, true, listUserGrantedAuthorities(user.getId()));
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
