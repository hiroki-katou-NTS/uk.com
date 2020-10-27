/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.share;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshstComRegLaborTime.
 */
@Setter
@Getter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class KshstRegLaborTime extends ContractUkJpaEntity {

	/** The exclus ver. */
	@Column(name = "EXCLUS_VER")
	private int exclusVer;

	/** The weekly time. */
	@Column(name = "WEEKLY_TIME")
	private int weeklyTime;

	/** The daily time. */
	@Column(name = "DAILY_TIME")
	private int dailyTime;
	
	public void setWeekStr(int w) {}
	
	public int getWeekStr() {
		return 0;
	}

}
