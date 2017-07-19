/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern;

import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternName;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPattern;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPatternPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaMonthlyPatternGetMemento.
 */
public class JpaMonthlyPatternGetMemento implements MonthlyPatternGetMemento{
	
	/** The monthly pattern. */
	private KmpstMonthPattern monthlyPattern;
	
	/**
	 * Instantiates a new jpa monthly pattern get memento.
	 *
	 * @param monthlyPattern the monthly pattern
	 */
	public JpaMonthlyPatternGetMemento(KmpstMonthPattern monthlyPattern) {
		if(monthlyPattern.getKmpstMonthPatternPK() == null){
			monthlyPattern.setKmpstMonthPatternPK(new KmpstMonthPatternPK());
		}
		this.monthlyPattern = monthlyPattern;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.monthlyPattern.getKmpstMonthPatternPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternGetMemento#
	 * getMonthlyPatternCode()
	 */
	@Override
	public MonthlyPatternCode getMonthlyPatternCode() {
		return new MonthlyPatternCode(this.monthlyPattern.getKmpstMonthPatternPK().getPatternCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternGetMemento#
	 * getMonthlyPatternName()
	 */
	@Override
	public MonthlyPatternName getMonthlyPatternName() {
		return new MonthlyPatternName(this.monthlyPattern.getPatternName());
	}

}
