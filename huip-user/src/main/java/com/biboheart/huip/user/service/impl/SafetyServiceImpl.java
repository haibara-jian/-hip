package com.biboheart.huip.user.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biboheart.brick.exception.BhException;
import com.biboheart.brick.utils.CheckUtils;
import com.biboheart.brick.utils.TimeUtils;
import com.biboheart.huip.user.domain.Safety;
import com.biboheart.huip.user.repository.SafetyRepository;
import com.biboheart.huip.user.service.SafetyService;

@Service
public class SafetyServiceImpl implements SafetyService {
	@Autowired
	private SafetyRepository safetyRepository;

	@Override
	public Safety save(Safety safety) throws BhException {
		if (null == safety.getId()) {
			safety.setId(0L);
		}
		if (CheckUtils.isEmpty(safety.getUid())) {
			throw new BhException("必须关联到用户");
		}
		if (CheckUtils.isEmpty(safety.getUsername())) {
			if (!CheckUtils.isEmpty(safety.getMobile())) {
				safety.setUsername(safety.getMobile());
			}
		}
		if (CheckUtils.isEmpty(safety.getUsername())) {
			throw new BhException("用户名不能为空");
		}
		Safety source = safetyRepository.findByUid(safety.getUid());
		if (null != source) {
			safety.setId(source.getId());
		}
		source = safetyRepository.findByUsername(safety.getUsername());
		if (null != source) {
			if (!safety.getUid().equals(source.getUid())) {
				throw new BhException("用户名已经被占用");
			}
			if (!source.getId().equals(safety.getId())) {
				if (CheckUtils.isEmpty(safety.getId())) {
					safety.setId(source.getId());
				} else {
					safetyRepository.deleteById(source.getId());
				}
			}
		}
		source = safetyRepository.findByMobile(safety.getMobile());
		if (null != source) {
			if (!safety.getUid().equals(source.getUid())) {
				throw new BhException("用户名已经被占用");
			}
			if (!source.getId().equals(safety.getId())) {
				if (CheckUtils.isEmpty(safety.getId())) {
					safety.setId(source.getId());
				} else {
					safetyRepository.deleteById(source.getId());
				}
			}
		}
		if (!CheckUtils.isEmpty(safety.getPassword()) && safety.getPassword().length() != 32) {
			safety.setPassword(DigestUtils.md5Hex(safety.getPassword()));
		}
		Long now = TimeUtils.getCurrentTimeInMillis();
		if (CheckUtils.isEmpty(safety.getCreateTime())) {
			safety.setCreateTime(now);
		}
		safety.setUpdateTime(now);
		safety = safetyRepository.save(safety);
		return safety;
	}

	@Override
	public void delete(Long id, Long uid) {
		if (!CheckUtils.isEmpty(uid)) {
			safetyRepository.deleteByUid(uid);
		}
		if (!CheckUtils.isEmpty(id)) {
			safetyRepository.deleteById(id);
		}
	}
	
	@Override
	public Safety load(Long uid, String username, String mobile) {
		Safety safety = null;
		if (!CheckUtils.isEmpty(uid)) {
			safety = safetyRepository.findByUid(uid);
		}
		if (null == safety && !CheckUtils.isEmpty(username)) {
			safety = safetyRepository.findByUsername(username);
		}
		if (null == safety && !CheckUtils.isEmpty(mobile)) {
			safety = safetyRepository.findByMobile(mobile);
		}
		return safety;
	}

}
