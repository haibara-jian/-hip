package com.biboheart.huip.reservation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 检查类别
 * @author crj
 *
 */
@Data
@Entity
@Table(name = "bh_reservation_inspect_type")
public class InspectType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String sn; // 编号
	private String name; // 检查类别名称
	private String remark; // 备注
	private Long createTime; // 创建时间
	private Long updateTime; // 更新时间
}
