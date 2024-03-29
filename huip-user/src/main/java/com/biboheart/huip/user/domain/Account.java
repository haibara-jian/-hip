package com.biboheart.huip.user.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "bh_user_account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // ID
	private String sn; // 账户编号
	private String username; // 用户名
	private String mobile; // 手机号
	private String password; // 密码
	private Long createTime; // 创建时间
	private Long updateTime; // 最后修改时间
}
