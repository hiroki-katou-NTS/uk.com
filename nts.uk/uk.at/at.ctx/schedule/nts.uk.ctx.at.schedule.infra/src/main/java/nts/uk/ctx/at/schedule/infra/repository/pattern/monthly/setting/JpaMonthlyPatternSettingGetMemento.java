/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern.monthly.setting;

import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.MonthlyPatternSettingGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.setting.KscmtMonthPatternSet;

/**
 * The Class JpaMonthlyPatternSettingGetMemento.
 */
public class JpaMonthlyPatternSettingGetMemento implements MonthlyPatternSettingGetMemento{

	/** The monthly pattern seting. */
	private KscmtMonthPatternSet monthlyPatternSeting;
	
	/**
	 * Instantiates a new jpa monthly pattern setting get memento.
	 *
	 * @param monthlyPatternSeting the monthly pattern seting
	 */
	public JpaMonthlyPatternSettingGetMemento(KscmtMonthPatternSet monthlyPatternSeting) {
		this.monthlyPatternSeting = monthlyPatternSeting;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.
	 * MonthlyPatternSettingGetMemento#getMonthlyPatternCode()
	 */
	@Override
	public MonthlyPatternCode getMonthlyPatternCode() {
		return new MonthlyPatternCode(this.monthlyPatternSeting.getMPatternCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.setting.
	 * MonthlyPatternSettingGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.monthlyPatternSeting.getSid();
	}

}
