package com.biboheart.huip.user.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biboheart.brick.exception.BhException;
import com.biboheart.brick.utils.CheckUtils;
import com.biboheart.brick.utils.TimeUtils;
import com.biboheart.huip.user.domain.Account;
import com.biboheart.huip.user.repository.AccountRepository;
import com.biboheart.huip.user.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account save(Account account) throws BhException {
		if (null == account.getId()) {
			account.setId(0L);
		}
		if (CheckUtils.isEmpty(account.getUid())) {
			throw new BhException("必须关联到用户");
		}
		if (CheckUtils.isEmpty(account.getUsername())) {
			if (!CheckUtils.isEmpty(account.getMobile())) {
				account.setUsername(account.getMobile());
			}
		}
		if (CheckUtils.isEmpty(account.getUsername())) {
			throw new BhException("用户名不能为空");
		}
		Account source = accountRepository.findByUid(account.getUid());
		if (null != source) {
			account.setId(source.getId());
		}
		source = accountRepository.findByUsername(account.getUsername());
		if (null != source) {
			if (!account.getUid().equals(source.getUid())) {
				throw new BhException("用户名已经被占用");
			}
			if (!source.getId().equals(account.getId())) {
				if (CheckUtils.isEmpty(account.getId())) {
					account.setId(source.getId());
				} else {
					accountRepository.deleteById(source.getId());
				}
			}
		}
		source = accountRepository.findByMobile(account.getMobile());
		if (null != source) {
			if (!account.getUid().equals(source.getUid())) {
				throw new BhException("用户名已经被占用");
			}
			if (!source.getId().equals(account.getId())) {
				if (CheckUtils.isEmpty(account.getId())) {
					account.setId(source.getId());
				} else {
					accountRepository.deleteById(source.getId());
				}
			}
		}
		if (!CheckUtils.isEmpty(account.getPassword()) && account.getPassword().length() != 32) {
			account.setPassword(DigestUtils.md5Hex(account.getPassword()));
		}
		Long now = TimeUtils.getCurrentTimeInMillis();
		if (CheckUtils.isEmpty(account.getCreateTime())) {
			account.setCreateTime(now);
		}
		account.setUpdateTime(now);
		account = accountRepository.save(account);
		return account;
	}

	@Override
	public void delete(Long id, Long uid) {
		if (!CheckUtils.isEmpty(uid)) {
			accountRepository.deleteByUid(uid);
		}
		if (!CheckUtils.isEmpty(id)) {
			accountRepository.deleteById(id);
		}
	}
	
	@Override
	public Account load(Long uid, String username, String mobile) {
		Account account = null;
		if (!CheckUtils.isEmpty(uid)) {
			account = accountRepository.findByUid(uid);
		}
		if (null == account && !CheckUtils.isEmpty(username)) {
			account = accountRepository.findByUsername(username);
		}
		if (null == account && !CheckUtils.isEmpty(mobile)) {
			account = accountRepository.findByMobile(mobile);
		}
		return account;
	}

}
