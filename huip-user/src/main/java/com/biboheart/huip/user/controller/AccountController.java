package com.biboheart.huip.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biboheart.brick.exception.BhException;
import com.biboheart.brick.model.BhResponseResult;
import com.biboheart.brick.utils.CheckUtils;
import com.biboheart.huip.user.domain.Account;
import com.biboheart.huip.user.domain.User;
import com.biboheart.huip.user.service.AccountService;
import com.biboheart.huip.user.service.UserService;

@RestController
public class AccountController {
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/userapi/account/save", method = {RequestMethod.POST})
	public BhResponseResult<?> save(Account account) throws BhException {
		User user = userService.load(account.getUid(), null);
		if (null == user) {
			// 如果不是对某个用户设置登录参数则创建一个新用户，用户注册时
			user = new User();
			String name = account.getUsername();
			if (CheckUtils.isEmpty(name)) {
				name = account.getMobile();
			}
			user.setName(name);
			user = userService.save(user);
		}
		account.setUid(user.getId());
		account = accountService.save(account);
		return new BhResponseResult<>(0, "success", user);
	}

}
