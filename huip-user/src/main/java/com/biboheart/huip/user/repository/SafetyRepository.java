package com.biboheart.huip.user.repository;

import org.springframework.transaction.annotation.Transactional;

import com.biboheart.huip.user.basejpa.CustomRepository;
import com.biboheart.huip.user.domain.Safety;

public interface SafetyRepository extends CustomRepository<Safety, Long> {
	Safety findByUid(Long uid);
	
	Safety findByUsername(String username);
	
	Safety findByMobile(String mobile);
	
	@Transactional
	void deleteByUid(Long uid);
}
