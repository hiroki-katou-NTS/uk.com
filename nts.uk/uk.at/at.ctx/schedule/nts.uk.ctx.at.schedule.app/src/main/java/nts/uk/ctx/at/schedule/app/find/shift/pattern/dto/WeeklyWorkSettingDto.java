/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.DayOfWeek;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class WeeklyWorkSettingDto.
 */
@Getter
@Setter
public class WeeklyWorkSettingDto implements WeeklyWorkSettingSetMemento{

	/** The day of week. */
	private int dayOfWeek;
	
	/** The workday division. */
	private int workdayDivision;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingSetMemento
	 * #setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// No thing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingSetMemento
	 * #setDayOfWeek(nts.uk.ctx.at.schedule.dom.shift.pattern.work.DayOfWeek)
	 */
	@Override
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek.value;
	}

	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSettingSetMemento
	 * #setWorkdayDivision(nts.uk.ctx.at.schedule.dom.shift.basicworkregister.
	 * WorkdayDivision)
	 */
	@Override
	public void setWorkdayDivision(WorkdayDivision workdayDivision) {
		this.workdayDivision = workdayDivision.value;
		
	}

	@Override
	public void setContractCode(String constractCode) {

	}
}
