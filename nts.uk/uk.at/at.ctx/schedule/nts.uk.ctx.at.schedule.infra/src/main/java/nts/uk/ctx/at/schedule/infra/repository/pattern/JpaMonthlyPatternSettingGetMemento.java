/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern;

import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSettingGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPatternSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPatternSetPK;

/**
 * The Class JpaMonthlyPatternSettingGetMemento.
 */
public class JpaMonthlyPatternSettingGetMemento implements MonthlyPatternSettingGetMemento{

	/** The monthly pattern seting. */
	private KmpstMonthPatternSet monthlyPatternSeting;
	
	/**
	 * Instantiates a new jpa monthly pattern setting get memento.
	 *
	 * @param monthlyPatternSeting the monthly pattern seting
	 */
	public JpaMonthlyPatternSettingGetMemento(KmpstMonthPatternSet monthlyPatternSeting) {
		if(monthlyPatternSeting.getKmpstMonthPatternSetPK() == null){
			monthlyPatternSeting.setKmpstMonthPatternSetPK(new KmpstMonthPatternSetPK());
		}
		this.monthlyPatternSeting = monthlyPatternSeting;
	}
	
	/**
	 * Gets the monthly pattern code.
	 *
	 * @return the monthly pattern code
	 */
	@Override
	public MonthlyPatternCode getMonthlyPatternCode() {
		return new MonthlyPatternCode(
				this.monthlyPatternSeting.getKmpstMonthPatternSetPK().getMonthPatternCd());
	}

	/**
	 * Gets the employee id.
	 *
	 * @return the employee id
	 */
	@Override
	public String getEmployeeId() {
		return this.monthlyPatternSeting.getKmpstMonthPatternSetPK().getSid();
	}

}
