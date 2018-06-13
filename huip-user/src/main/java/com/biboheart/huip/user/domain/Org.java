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
@Table(name = "bh_user_org")
public class Org implements Serializable {
	private static final long serialVersionUID = -2086039594378900727L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String sn; // 组织编号
	private String name; // 组织的名称
	private Integer otid; // 组织的类型ID
	private String otsn; // 类型编号
	private String otname; // 组织类型名称，冗余处理，方便使用
	private Integer pid; // 父组织ID，顶层时为0
	private String pname; // 父组织名称
	private String path; // 组织路径，如：XX集团>XX医院>XX科室
}
