package com.biboheart.huip.reservation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 检查预约
 * @author crj
 *
 */
@Data
@Entity
@Table(name = "bh_reservation_inspect")
public class Inspect {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String sn; // 申请编号
	private Integer device; // 设备ID
	private String dsn; // 设备编号
	private String dname; // 设备名称
	private Integer project; // 项目ID
	private String psn; // 项目编号
	private String pname; // 项目名称
	private Long pat; // 患者ID
	private String patName; // 患者姓名
	private String patSn; // 患者编号
	private String medicalNumber; // 患者就诊号
	private Integer source; // 来源，1：门诊，2：住院，3：病房，4：体检
	private Long time; // 使用时间
	private Long duration; // 时长
	private Long creator; // 创建者
	private String creatorName; // 创建者姓名
	private Long createTime; // 创建时间
	private Long upeateTime; // 更新时间
	private Integer charged; // 是否已经收费0：未收费，1：已收费
	private Integer state; // 1：已登记，2：已申请，3：已预约，
	private String remark; // 备注
}
