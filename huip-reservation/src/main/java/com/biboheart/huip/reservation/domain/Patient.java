package com.biboheart.huip.reservation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "bh_reservation_patient")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String sn; // 编号
	private Long pat; // 患者ID
	private String name; // 姓名
	private Integer sex; // 性别
	private Integer age; // 年龄
	private String idcard; // 身份证号码
	private String phone; // 联系电话
	private String spell; // 拼音首字母
	private Long createTime; // 创建时间
	private Long upeateTime; // 更新时间
}
