/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern.monthly.setting;

import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.MonthlyPatternSettingSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.setting.KscmtMonthPatternSet;

/**
 * The Class JpaMonthlyPatternSettingSetMemento.
 */
public class JpaMonthlyPatternSettingSetMemento implements MonthlyPatternSettingSetMemento{

	private KscmtMonthPatternSet monthlyPatternSeting;
	
	/**
	 * Instantiates a new jpa monthly pattern setting set memento.
	 *
	 * @param monthlyPatternSeting the monthly pattern seting
	 */
	public JpaMonthlyPatternSettingSetMemento(KscmtMonthPatternSet monthlyPatternSeting) {
		this.monthlyPatternSeting = monthlyPatternSeting;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSettingSetMemento#
	 * setMonthlyPatternCode(nts.uk.ctx.at.schedule.dom.shift.pattern.
	 * MonthlyPatternCode)
	 */
	@Override
	public void setMonthlyPatternCode(MonthlyPatternCode monthlyPatternCode) {
		this.monthlyPatternSeting.setMPatternCd(monthlyPatternCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSettingSetMemento#
	 * setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.monthlyPatternSeting.setSid(employeeId);
	}

}
