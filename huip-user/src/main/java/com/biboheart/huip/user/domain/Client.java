package com.biboheart.huip.user.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "bh_user_client")
public class Client implements Serializable {
	private static final long serialVersionUID = -6421664309310055644L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String clientName; // 客户端名称
	private String clientId; // 客户端ID
	private String resourceIds;
	private String clientSecret; // 客户端密码
	private String scope; // 客户端权限范围
	private String authorizedGrantTypes; // 客户端可请求的认证类型
	@Column(length = 4096)
	private String webServerRedirectUri; // 跳转地址
	private String authorities; // 权限
	private Integer accessTokenValidity; // token有效时间
	private Integer refreshTokenValidity; // 刷新token有效时间
	private String additionalInformation; // 补充信息
	private String autoapprove;
	private String registeredRedirectUri;
	private Long createTime; // 创建时间
	private int self = 1; // 是不是自己平台的项目
}
