/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern;

import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSettingSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPatternSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPatternSetPK;

/**
 * The Class JpaMonthlyPatternSettingSetMemento.
 */
public class JpaMonthlyPatternSettingSetMemento implements MonthlyPatternSettingSetMemento{

	private KmpstMonthPatternSet monthlyPatternSeting;
	
	/**
	 * Instantiates a new jpa monthly pattern setting set memento.
	 *
	 * @param monthlyPatternSeting the monthly pattern seting
	 */
	public JpaMonthlyPatternSettingSetMemento(KmpstMonthPatternSet monthlyPatternSeting) {
		if(monthlyPatternSeting.getKmpstMonthPatternSetPK() == null){
			monthlyPatternSeting.setKmpstMonthPatternSetPK(new KmpstMonthPatternSetPK());
		}
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
		this.monthlyPatternSeting.getKmpstMonthPatternSetPK()
				.setMonthPatternCd(monthlyPatternCode.v());
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
		this.monthlyPatternSeting.getKmpstMonthPatternSetPK().setSid(employeeId);

	}

}
