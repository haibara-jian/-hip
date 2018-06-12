package com.biboheart.huip.user.service;

import com.biboheart.brick.exception.BhException;
import com.biboheart.huip.user.domain.Account;

public interface AccountService {
	public Account save(Account account) throws BhException;
	
	public void delete(Long id, Long uid);
	
	public Account load(Long uid, String username, String mobile);
}
