/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern.monthly;

import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternGetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternName;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.KscmtMonthPattern;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.KscmtMonthPatternPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaMonthlyPatternGetMemento.
 */
public class JpaMonthlyPatternGetMemento implements MonthlyPatternGetMemento{
	
	/** The monthly pattern. */
	private KscmtMonthPattern monthlyPattern;
	
	/**
	 * Instantiates a new jpa monthly pattern get memento.
	 *
	 * @param monthlyPattern the monthly pattern
	 */
	public JpaMonthlyPatternGetMemento(KscmtMonthPattern monthlyPattern) {
		if(monthlyPattern.getKscmtMonthPatternPK() == null){
			monthlyPattern.setKscmtMonthPatternPK(new KscmtMonthPatternPK());
		}
		this.monthlyPattern = monthlyPattern;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.monthlyPattern.getKscmtMonthPatternPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternGetMemento#
	 * getMonthlyPatternCode()
	 */
	@Override
	public MonthlyPatternCode getMonthlyPatternCode() {
		return new MonthlyPatternCode(this.monthlyPattern.getKscmtMonthPatternPK().getMPatternCd());
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

	@Override
	public String getContractCd() {
		return this.monthlyPattern.getContractCd();
	}

}
