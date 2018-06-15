package com.biboheart.huip.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.biboheart.brick.exception.BhException;
import com.biboheart.brick.utils.CheckUtils;
import com.biboheart.brick.utils.TimeUtils;
import com.biboheart.huip.user.domain.Account;
import com.biboheart.huip.user.domain.User;
import com.biboheart.huip.user.repository.AccountRepository;
import com.biboheart.huip.user.repository.UserRepository;
import com.biboheart.huip.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public User current() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(null == authentication) {
			return null;
		}
		String username = null;
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			username = authentication.getName();
		}
		if(CheckUtils.isEmpty(username)) {
			return null;
		}
		Account account = accountRepository.findByUsername(username);
		if (null == account || CheckUtils.isEmpty(account.getUid())) {
			return null;
		}
		User user = userRepository.findById(account.getUid()).get();
		return user;
	}

	@Override
	public User save(User user) throws BhException {
		if (null == user.getId()) {
			user.setId(0L);
		}
		// 以下用到了 com.biboheart.brick 中的BhException,CheckUtils.isEmpty,TimeUtils.getCurrentTimeInMillis
		if (CheckUtils.isEmpty(user.getName())) {
			throw new BhException("用户名称不能为空");
		}
		Long now = TimeUtils.getCurrentTimeInMillis(); // 取当前时间戳
		if (CheckUtils.isEmpty(user.getCreateTime())) {
			user.setCreateTime(now);
		}
		user.setUpdateTime(now);
		user = userRepository.save(user);
		return user;
	}

	@Override
	public User delete(Long id) {
		if (CheckUtils.isEmpty(id)) {
			return null;
		}
		User user = userRepository.findById(id).get();
		if (null != user) {
			userRepository.delete(user);
		}
		return user;
	}

	@Override
	public User load(Long id, String username) {
		User user = null;
		if (null == user && !CheckUtils.isEmpty(username)) {
			Account account = accountRepository.findByUsername(username);
			if (null != account && !CheckUtils.isEmpty(account.getUid())) {
				user = userRepository.findById(account.getUid()).get();
			}
		}
		if (null == user && !CheckUtils.isEmpty(id)) {
			user = userRepository.findById(id).get();
		}
		return user;
	}

	@Override
	public List<User> list() {
		List<User> users = userRepository.findAll();
		return users;
	}

	@Override
	public Page<User> find() {
		Page<User> users = userRepository.findAll((Pageable)null);
		return users;
	}

}
