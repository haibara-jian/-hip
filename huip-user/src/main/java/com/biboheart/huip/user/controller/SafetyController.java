package com.biboheart.huip.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biboheart.brick.exception.BhException;
import com.biboheart.brick.model.BhResponseResult;
import com.biboheart.brick.utils.CheckUtils;
import com.biboheart.huip.user.domain.Safety;
import com.biboheart.huip.user.domain.User;
import com.biboheart.huip.user.service.SafetyService;
import com.biboheart.huip.user.service.UserService;

@RestController
public class SafetyController {
	@Autowired
	private UserService userService;
	@Autowired
	private SafetyService safetyService;
	
	@RequestMapping(value = "/userapi/safety/save", method = {RequestMethod.POST})
	public BhResponseResult<?> save(Safety safety) throws BhException {
		User user = userService.load(safety.getUid());
		if (null == user) {
			// 如果不是对某个用户设置登录参数则创建一个新用户，用户注册时
			user = new User();
			String name = safety.getUsername();
			if (CheckUtils.isEmpty(name)) {
				name = safety.getMobile();
			}
			user.setName(name);
			user = userService.save(user);
		}
		safety.setUid(user.getId());
		safety = safetyService.save(safety);
		return new BhResponseResult<>(0, "success", user);
	}

}
