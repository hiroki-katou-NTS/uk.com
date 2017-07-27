/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern;

import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternName;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpmtMonthPattern;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KmpmtMonthPatternPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaMonthlyPatternGetMemento.
 */
public class JpaMonthlyPatternGetMemento implements MonthlyPatternGetMemento{
	
	/** The monthly pattern. */
	private KmpmtMonthPattern monthlyPattern;
	
	/**
	 * Instantiates a new jpa monthly pattern get memento.
	 *
	 * @param monthlyPattern the monthly pattern
	 */
	public JpaMonthlyPatternGetMemento(KmpmtMonthPattern monthlyPattern) {
		if(monthlyPattern.getKmpmtMonthPatternPK() == null){
			monthlyPattern.setKmpmtMonthPatternPK(new KmpmtMonthPatternPK());
		}
		this.monthlyPattern = monthlyPattern;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.monthlyPattern.getKmpmtMonthPatternPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternGetMemento#
	 * getMonthlyPatternCode()
	 */
	@Override
	public MonthlyPatternCode getMonthlyPatternCode() {
		return new MonthlyPatternCode(this.monthlyPattern.getKmpmtMonthPatternPK().getMPatternCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternGetMemento#
	 * getMonthlyPatternName()
	 */
	@Override
	public MonthlyPatternName getMonthlyPatternName() {
		return new MonthlyPatternName(this.monthlyPattern.getMPatternName());
	}

}
