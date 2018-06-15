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
@Table(name = "bh_user_user_org")
public class UserOrg implements Serializable {
	private static final long serialVersionUID = 2439488455909891027L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long uid; // 用户ID
	private Integer oid; // 组织ID
	private Integer otid; // 组织的类型ID
	private String otname; // 组织类型名称
	private String oname; // 组织名称
	private String opath; // 组织路径，如：XX集团>XX医院>XX科室
}
