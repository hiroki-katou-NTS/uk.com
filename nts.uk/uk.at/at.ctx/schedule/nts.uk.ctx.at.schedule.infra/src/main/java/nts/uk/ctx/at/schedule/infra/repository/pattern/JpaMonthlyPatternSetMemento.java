/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern;

import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternName;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPattern;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpstMonthPatternPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaMonthlyPatternSetMemento.
 */
public class JpaMonthlyPatternSetMemento implements MonthlyPatternSetMemento {
	
	/** The mothly pattern. */
	private KmpstMonthPattern mothlyPattern;
	
	/**
	 * Instantiates a new jpa monthly pattern set memento.
	 *
	 * @param mothlyPattern the mothly pattern
	 */
	public JpaMonthlyPatternSetMemento(KmpstMonthPattern mothlyPattern) {
		if(mothlyPattern.getKmpstMonthPatternPK() == null){
			mothlyPattern.setKmpstMonthPatternPK(new KmpstMonthPatternPK());
		}
		this.mothlyPattern = mothlyPattern;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.mothlyPattern.getKmpstMonthPatternPK().setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSetMemento#
	 * setMonthlyPatternCode(nts.uk.ctx.at.schedule.dom.shift.pattern.
	 * MonthlyPatternCode)
	 */
	@Override
	public void setMonthlyPatternCode(MonthlyPatternCode monthlyPatternCode) {
		this.mothlyPattern.getKmpstMonthPatternPK().setPatternCd(monthlyPatternCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSetMemento#
	 * setMonthlyPatternName(nts.uk.ctx.at.schedule.dom.shift.pattern.
	 * MonthlyPatternName)
	 */
	@Override
	public void setMonthlyPatternName(MonthlyPatternName monthlyPatternName) {
		this.mothlyPattern.setPatternName(monthlyPatternName.v());
	}

}
