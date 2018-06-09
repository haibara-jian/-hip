package com.biboheart.huip.user.service;

import com.biboheart.brick.exception.BhException;
import com.biboheart.huip.user.domain.Safety;

public interface SafetyService {
	public Safety save(Safety safety) throws BhException;
	
	public void delete(Long id, Long uid);
	
	public Safety load(Long uid, String username, String mobile);
}
