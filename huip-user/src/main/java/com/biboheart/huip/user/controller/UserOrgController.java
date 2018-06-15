package com.biboheart.huip.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biboheart.brick.exception.BhException;
import com.biboheart.brick.model.BhResponseResult;
import com.biboheart.huip.user.domain.UserOrg;
import com.biboheart.huip.user.service.UserOrgService;

@RestController
public class UserOrgController {
	@Autowired
	private UserOrgService userOrgService;

	/**
	 * 保存用户组织
	 * @param uo
	 * @return
	 * @throws BhException
	 */
	@RequestMapping(value = "/userapi/user/userOrg/save", method = { RequestMethod.POST })
	public BhResponseResult<?> save(UserOrg uo) throws BhException {
		uo = userOrgService.save(uo);
		return new BhResponseResult<>(0, "success", uo);
	}
	
	/**
	 * 删除用户组织
	 * @param uid 用户ID
	 * @param oid 组织ID
	 * @return
	 */
	@RequestMapping(value = "/userapi/user/userOrg/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public BhResponseResult<?> delete(Long uid, Integer oid) {
		UserOrg uo = userOrgService.delete(null, uid, oid);
		return new BhResponseResult<>(0, "success", uo);
	}
	
	/**
	 * 用户的组织列表
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/userapi/user/userOrg/list", method = {RequestMethod.POST, RequestMethod.GET})
	public BhResponseResult<?> list(Long uid) {
		List<UserOrg> uos = userOrgService.list(uid);
		return new BhResponseResult<>(0, "success", uos);
	}
}
