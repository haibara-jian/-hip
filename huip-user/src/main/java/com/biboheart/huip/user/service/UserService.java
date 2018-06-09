package com.biboheart.huip.user.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.biboheart.brick.exception.BhException;
import com.biboheart.huip.user.domain.User;

public interface UserService {
	/**
	 * 保存用户信息
	 * 
	 * @param user
	 *            用户信息对象
	 * @return 保存成功后的用户信息或null
	 * @throws BhException 参数异常捕获
	 */
	public User save(User user) throws BhException;

	/**
	 * 删除用户
	 * 
	 * @param id
	 *            用户ID
	 * @return 返回删除成功的用户信息或null
	 */
	public User delete(Long id);

	/**
	 * 查询用户信息
	 * 
	 * @param id
	 *            用户ID
	 * @return 用户信息或null
	 */
	public User load(Long id);

	/**
	 * 用户列表
	 * 
	 * @return 返回用户对象列表
	 */
	public List<User> list();
	public Page<User> find();
}
