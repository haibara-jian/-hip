package com.biboheart.huip.user.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biboheart.brick.exception.BhException;
import com.biboheart.brick.model.BhResponseResult;
import com.biboheart.brick.utils.CheckUtils;
import com.biboheart.brick.utils.ListUtils;
import com.biboheart.brick.utils.PrimaryTransverter;
import com.biboheart.huip.user.domain.Org;
import com.biboheart.huip.user.domain.OrgType;
import com.biboheart.huip.user.domain.User;
import com.biboheart.huip.user.domain.UserOrg;
import com.biboheart.huip.user.service.OrgService;
import com.biboheart.huip.user.service.OrgTypeService;
import com.biboheart.huip.user.service.UserOrgService;
import com.biboheart.huip.user.service.UserService;

@RestController
public class OrgController {
	@Autowired
	private OrgService orgService;
	@Autowired
	private OrgTypeService orgTypeService;
	@Autowired
	private UserOrgService userOrgService;
	@Autowired
	private UserService userService;
	
	/**
	 * 保存组织
	 * @param org
	 * @return
	 * @throws BhException
	 */
	@RequestMapping(value = "/userapi/user/org/save", method = {RequestMethod.POST})
	public BhResponseResult<?> save(Org org) throws BhException {
		org = orgService.save(org);
		return new BhResponseResult<>(0, "success", org);
	}
	
	/**
	 * 删除组织
	 * @param id 组织ID
	 * @return
	 */
	@RequestMapping(value = "/userapi/user/org/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public BhResponseResult<?> delete(Integer id) {
		Org org = orgService.delete(id);
		return new BhResponseResult<>(0, "success", org);
	}
	
	/**
	 * 查询组织
	 * @param id 组织ID
	 * @param sn 组织编码
	 * @param pid 组织父ID
	 * @param name 组织名称（要与父ID配合两个参数一起查）
	 * @param parentTypeId 要与ID配合，如果这个值不为空则ID就作为这个值的辅助参数，查找ID的祖先中这个类型的组织
	 * @param parentTypeSn 同parentTypeId，要先把这个值转成parentTypeId后再查找
	 * @return
	 */
	@RequestMapping(value = "/userapi/user/org/load", method = {RequestMethod.POST, RequestMethod.GET})
	public BhResponseResult<?> load(Integer id, String sn, Integer pid, String name, Integer parentTypeId, String parentTypeSn) {
		if (CheckUtils.isEmpty(parentTypeId) && !CheckUtils.isEmpty(parentTypeSn)) {
			OrgType ot = orgTypeService.load(null, parentTypeSn);
			parentTypeId = ot.getId();
		}
		Org org = orgService.load(id, sn, pid, name, parentTypeId);
		return new BhResponseResult<>(0, "success", org);
	}
	
	/**
	 * 组织列表
	 * @param ids ID范围，对列表结果的ID筛选，多个用“,”分隔
	 * @param pids 父ID范围，多个用“,”分隔
	 * @param otids 结果组织类型范围，多个用“,”分隔
	 * @param otsns 组织类型编码范围，多个用“,”分隔
	 * @param descendant 查的结果是否查找子孙节点，值为1时，表示查找子孙节点
	 * @param parents 查的结果是否查找祖先节点，值为1时，表示查找的列表结果包含祖先节点
	 * @param match 模糊匹配值，它将模糊查找sn,name,otname三个项
	 * @return
	 */
	@RequestMapping(value = "/userapi/user/org/list", method = {RequestMethod.POST, RequestMethod.GET})
	public BhResponseResult<?> list(String ids, String pids, String otids, String otsns, Integer descendant, Integer parents, String match) {
		List<Integer> inIdList = PrimaryTransverter.idsStr2List(ids);
		List<Integer> inOtidList = PrimaryTransverter.idsStr2List(otids);
		if(!CheckUtils.isEmpty(otsns)) {
			String[] otSnArr = otsns.split(",");
			for(String otSn : otSnArr) {
				OrgType ot = orgTypeService.load(null, otSn);
				if(null == ot) {
					continue;
				}
				if(null == inOtidList) {
					inOtidList = new ArrayList<>();
				}
				if(!inOtidList.contains(ot.getId())) {
					inOtidList.add(ot.getId());
				}
			}
		}
		User user = userService.current();
		if (null != user) {
			List<UserOrg> uos = userOrgService.list(user.getId());
			List<Integer> userAllowOidList = null;
			if (!CheckUtils.isEmpty(uos)) {
				List<Integer> userOidList = new ArrayList<>();
				for (UserOrg uo : uos) {
					userOidList.add(uo.getOid());
				}
				userAllowOidList = orgService.listId(null, userOidList, null, 1, null);
			}
			if (CheckUtils.isEmpty(userAllowOidList)) {
				inIdList = new ArrayList<>();
				inIdList.add(0);
			} else {
				if (CheckUtils.isEmpty(inIdList)) {
					inIdList = userAllowOidList;
				} else {
					// 取交集
					inIdList = ListUtils.intersectionList(inIdList, userAllowOidList);
				}
			}
		}
		List<Integer> inPidList = PrimaryTransverter.idsStr2List(pids);
		List<Org> orgs = orgService.list(inIdList, inPidList, inOtidList, descendant, parents, match);
		return new BhResponseResult<>(0, "success", orgs);
	}
	
	/**
	 * 组织ID列表
	 * @param ids ID范围，对列表结果的ID筛选，多个用“,”分隔
	 * @param pids 父ID范围，多个用“,”分隔
	 * @param otids 结果组织类型范围，多个用“,”分隔
	 * @param otsns 组织类型编码范围，多个用“,”分隔
	 * @param descendant 查的结果是否查找子孙节点，值为1时，表示查找子孙节点
	 * @param parents 查的结果是否查找祖先节点，值为1时，表示查找的列表结果包含祖先节点
	 * @return
	 */
	@RequestMapping(value = "/userapi/user/org/listId", method = {RequestMethod.POST, RequestMethod.GET})
	public BhResponseResult<?> listId(String ids, String pids, String otids, String otsns, Integer descendant, Integer parents) {
		List<Integer> inIdList = PrimaryTransverter.idsStr2List(ids);
		List<Integer> inOtidList = PrimaryTransverter.idsStr2List(otids);
		if(!CheckUtils.isEmpty(otsns)) {
			String[] otSnArr = otsns.split(",");
			for(String otSn : otSnArr) {
				OrgType ot = orgTypeService.load(null, otSn);
				if(null == ot) {
					continue;
				}
				if(null == inOtidList) {
					inOtidList = new ArrayList<>();
				}
				if(!inOtidList.contains(ot.getId())) {
					inOtidList.add(ot.getId());
				}
			}
		}
		User user = userService.current();
		if (null != user) {
			List<UserOrg> uos = userOrgService.list(user.getId());
			List<Integer> userAllowOidList = null;
			if (!CheckUtils.isEmpty(uos)) {
				List<Integer> userOidList = new ArrayList<>();
				for (UserOrg uo : uos) {
					userOidList.add(uo.getOid());
				}
				userAllowOidList = orgService.listId(null, userOidList, null, 1, null);
			}
			if (CheckUtils.isEmpty(userAllowOidList)) {
				inIdList = new ArrayList<>();
				inIdList.add(0);
			} else {
				if (CheckUtils.isEmpty(inIdList)) {
					inIdList = userAllowOidList;
				} else {
					// 取交集
					inIdList = ListUtils.intersectionList(inIdList, userAllowOidList);
				}
			}
		}
		List<Integer> inPidList = PrimaryTransverter.idsStr2List(pids);
		List<Integer> idList = orgService.listId(inIdList, inPidList, inOtidList, descendant, parents);
		return new BhResponseResult<>(0, "success", idList);
	}
}
