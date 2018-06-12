package com.biboheart.huip.user.service;

import java.util.List;

import com.biboheart.brick.exception.BhException;
import com.biboheart.huip.user.domain.OrgType;
import com.biboheart.huip.user.domain.OrgTypeRule;

public interface OrgTypeService {
	/**
	 * 添加组织类型
	 * @param orgType 组织类型对象
	 * @return
	 */
	public OrgType save(OrgType orgType) throws BhException;
	
	/**
	 * 删除组织类型
	 * @param id 组织类型ID
	 * @return
	 */
	public OrgType delete(Integer id, String sn);
	
	/**
	 * 查询组织类型
	 * @param id
	 * @param sn
	 * @return
	 */
	public OrgType load(Integer id, String sn);
	
	/**
	 * 取所有类型
	 * @return
	 */
	public List<OrgType> list(List<Integer> ids, List<String> sns, List<Integer> pidList, Integer descendant, Integer self);
	public List<Integer> listId(List<String> sns, List<Integer> pidList, Integer descendant, Integer self);
	
	/**
	 * 添加组织类型规则
	 * @param pid 父类型ID
	 * @param cid 子类型ID
	 * @throws EberException 
	 */
	public OrgTypeRule addOrgTypeRule(Integer pid, Integer cid) throws BhException;
	
	public OrgTypeRule deleteOrgTypeRule(Integer pid, Integer cid) throws BhException;
}
