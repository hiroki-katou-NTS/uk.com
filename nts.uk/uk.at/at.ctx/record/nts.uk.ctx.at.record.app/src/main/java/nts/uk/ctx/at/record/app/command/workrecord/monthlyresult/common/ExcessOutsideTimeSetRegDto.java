/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.common;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.ExcessOutsideTimeSetRegGetMemento;

/**
 * The Class ExcessOutsideTimeSetRegDto.
 */
@Getter
@Setter
public class ExcessOutsideTimeSetRegDto implements ExcessOutsideTimeSetRegGetMemento {

	/** The legal over time work. */
	/* 法定内残業を含める. */
	private Boolean legalOverTimeWork;

	/** The legal holiday. */
	/* 法定外休出を含める. */
	private Boolean legalHoliday;

	/** The surcharge week month. */
	/* 週、月割増時間を集計する. */
	private Boolean surchargeWeekMonth;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.
	 * ExcessOutsideTimeSetRegGetMemento#getLegalOverTimeWork()
	 */
	@Override
	public Boolean getLegalOverTimeWork() {
		return this.legalOverTimeWork;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.
	 * ExcessOutsideTimeSetRegGetMemento#getLegalHoliday()
	 */
	@Override
	public Boolean getLegalHoliday() {
		return this.legalHoliday;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.monthlyresult.
	 * ExcessOutsideTimeSetRegGetMemento#getSurchargeWeekMonth()
	 */
	@Override
	public Boolean getSurchargeWeekMonth() {
		return this.surchargeWeekMonth;
	}

}
