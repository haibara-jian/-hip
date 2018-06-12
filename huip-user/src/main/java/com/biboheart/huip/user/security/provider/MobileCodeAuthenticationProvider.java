package com.biboheart.huip.user.security.provider;

import java.util.HashSet;
import java.util.Set;

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
import com.biboheart.huip.user.security.tokens.MobileCodeAuthenticationToken;
import com.biboheart.huip.user.service.AccountService;
import com.biboheart.huip.user.service.UserService;

public class MobileCodeAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	private AccountService accountService;
	@Autowired
	private UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		System.out.println(this.getClass().getName() + " authenticate");
		String mobile = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		String code = (String) authentication.getCredentials();
		if (CheckUtils.isEmpty(code)) {
			throw new BadCredentialsException("验证码不能为空");
		}
		Account account = accountService.load(null, null, mobile);
		if (null == account) {
			throw new BadCredentialsException("用户不存在");
		}
		User user = userService.load(account.getUid());
		if (null == user) {
			throw new BadCredentialsException("用户不存在");
		}
		// 手机号验证码业务还没有开发，先用4个0验证
		if (!code.equals("0000")) {
			throw new BadCredentialsException("验证码不正确");
		}
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(account.getUsername(), code, listUserGrantedAuthorities(user.getId()));
		result.setDetails(authentication.getDetails());
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (MobileCodeAuthenticationToken.class.isAssignableFrom(authentication));
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
