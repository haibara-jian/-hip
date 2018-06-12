package com.biboheart.huip.user.repository;

import org.springframework.transaction.annotation.Transactional;

import com.biboheart.huip.user.basejpa.CustomRepository;
import com.biboheart.huip.user.domain.Account;

public interface AccountRepository extends CustomRepository<Account, Long> {
	Account findByUid(Long uid);
	
	Account findByUsername(String username);
	
	Account findByMobile(String mobile);
	
	@Transactional
	void deleteByUid(Long uid);
}
