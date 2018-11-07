package com.biboheart.huip.reservation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "bh_reservation_project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String sn; // 编码
	private String name; // 名称
	private Integer state; // 状态1：正常，2：暂停
	private String remark; // 备注
	private Long createTime; // 创建时间
	private Long updateTime; // 最后更新时间
}
