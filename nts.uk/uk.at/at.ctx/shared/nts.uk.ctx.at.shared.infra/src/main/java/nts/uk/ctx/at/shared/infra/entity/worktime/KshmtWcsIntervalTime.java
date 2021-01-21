/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.worktime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshmtWcsIntervalTime.
 */
@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class KshmtWcsIntervalTime extends ContractUkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The interval time. */
	@Column(name = "INTERVAL_TIME")
	private int intervalTime;

	/** The use interval time. */
	@Column(name = "USE_INTERVAL_TIME")
	private int useIntervalTime;

	/** The raising salary set. */
	@Column(name = "RAISING_SALARY_SET")
	private String raisingSalarySet;

	/** The nur timezone work use. */
	@Column(name = "NUR_TIMEZONE_WORK_USE")
	private int nurTimezoneWorkUse;

	/** The emp time deduct. */
	@Column(name = "EMP_TIME_DEDUCT")
	private int empTimeDeduct;

	/** The child care work use. */
	@Column(name = "CHILD_CARE_WORK_USE")
	private int childCareWorkUse;

	/** The late night unit. */
	@Column(name = "LATE_NIGHT_UNIT")
	private int lateNightUnit;

	/** The late night rounding. */
	@Column(name = "LATE_NIGHT_ROUNDING")
	private int lateNightRounding;

}
