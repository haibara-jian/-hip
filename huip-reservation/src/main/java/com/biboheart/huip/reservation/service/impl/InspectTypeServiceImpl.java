package com.biboheart.huip.reservation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biboheart.brick.exception.BhException;
import com.biboheart.brick.utils.CheckUtils;
import com.biboheart.brick.utils.TimeUtils;
import com.biboheart.huip.reservation.domain.InspectType;
import com.biboheart.huip.reservation.repository.InspectTypeRepository;
import com.biboheart.huip.reservation.service.InspectTypeService;

@Service
public class InspectTypeServiceImpl implements InspectTypeService {
	@Autowired
	private InspectTypeRepository inspectTypeRepository;

	@Override
	public InspectType save(InspectType it) throws BhException {
		if (null == it.getId()) {
			it.setId(0);
		}
		if (CheckUtils.isEmpty(it.getSn())) {
			throw new BhException("编号不能为空");
		}
		if (CheckUtils.isEmpty(it.getName())) {
			throw new BhException("名称不能为空");
		}
		if (null != inspectTypeRepository.findBySnAndIdNot(it.getSn(), it.getId())) {
			throw new BhException("编号已经存在");
		}
		if (null != inspectTypeRepository.findByNameAndIdNot(it.getName(), it.getId())) {
			throw new BhException("名称已经存在");
		}
		Long now = TimeUtils.getCurrentTimeInMillis();
		if (CheckUtils.isEmpty(it.getCreateTime())) {
			it.setCreateTime(now);
		}
		it.setUpdateTime(now);
		it = inspectTypeRepository.save(it);
		return it;
	}

	@Override
	public InspectType delete(Integer id) {
		InspectType it = null;
		if (null == it && !CheckUtils.isEmpty(id)) {
			try {
				it = inspectTypeRepository.findById(id).get();
			} catch (Exception e) {
				it = null;
			}
		}
		if (null != it) {
			inspectTypeRepository.delete(it);
		}
		return it;
	}

	@Override
	public InspectType load(Integer id) {
		InspectType it = null;
		if (null == it && !CheckUtils.isEmpty(id)) {
			try {
				it = inspectTypeRepository.findById(id).get();
			} catch (Exception e) {
				it = null;
			}
		}
		return it;
	}

	@Override
	public List<InspectType> list() {
		List<InspectType> its = inspectTypeRepository.findAll();
		return its;
	}

}
