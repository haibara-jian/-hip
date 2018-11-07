package com.biboheart.huip.reservation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "bh_reservation_device")
public class Device {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String sn; // 编码
	private String name; // 名称
	private Integer state; // 状态 1：正常，2：异常，3：暂停使用
	private String remark; // 备注，对状态的补充描述
	private String site; // 位置
	private Long createTime; // 创建时间
	private Long updateTime; // 最后一次修改时间
}
