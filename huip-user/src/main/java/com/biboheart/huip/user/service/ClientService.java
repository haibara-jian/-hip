package com.biboheart.huip.user.service;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.biboheart.brick.exception.BhException;
import com.biboheart.huip.user.domain.Client;

public interface ClientService {
	/**
	 * 添加/修改信息
	 * 
	 * @param client
	 * @return
	 * @throws EberException 
	 */
	public Client save(Client client) throws BhException;

	/**
	 * 根据id删除信息
	 * 
	 * @param id
	 * @return
	 * @throws EberException 
	 */
	public Client delete(Integer id);

	/**
	 * 根据客户端名称加载信息
	 * 
	 * @param name
	 * @return
	 */
	public Client load(Integer id, String name, String clientId);

	/**
	 * 加载所有信息
	 * 
	 * @return
	 */
	public List<Client> list();
	
	/**
	 * 当前请求的客户端
	 * @return
	 * @throws EberException 
	 */
	public Client current();
	
	public Set<GrantedAuthority> listClientGrantedAuthorities(String clientId);
}
