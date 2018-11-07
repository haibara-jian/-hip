package com.biboheart.huip.user.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "bh_user_user")
public class User implements Serializable {
	private static final long serialVersionUID = -989635790212353396L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 用户ID
	private String sn; // 用户编号
	private String account; // 账户编号，对应Account中的sn
	private String name; // 用户名称
	private String phone; // 电话号码
	private Long birthday; // 生日
	private Long createTime; // 创建时间
	private Long updateTime; // 最后修改时间
}
