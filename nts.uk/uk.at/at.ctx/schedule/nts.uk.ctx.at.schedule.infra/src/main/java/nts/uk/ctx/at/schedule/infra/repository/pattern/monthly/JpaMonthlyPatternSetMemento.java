/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern.monthly;

import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternName;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.KscmtMonthPattern;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.monthly.KscmtMonthPatternPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class JpaMonthlyPatternSetMemento.
 */
public class JpaMonthlyPatternSetMemento implements MonthlyPatternSetMemento {
	
	/** The mothly pattern. */
	private KscmtMonthPattern mothlyPattern;
	
	/**
	 * Instantiates a new jpa monthly pattern set memento.
	 *
	 * @param mothlyPattern the mothly pattern
	 */
	public JpaMonthlyPatternSetMemento(KscmtMonthPattern mothlyPattern) {
		if(mothlyPattern.getKscmtMonthPatternPK() == null){
			mothlyPattern.setKscmtMonthPatternPK(new KscmtMonthPatternPK());
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
		this.mothlyPattern.getKscmtMonthPatternPK().setCid(companyId.v());
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
		this.mothlyPattern.getKscmtMonthPatternPK().setMPatternCd(monthlyPatternCode.v());
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
		this.mothlyPattern.setMPatternName(monthlyPatternName.v());
	}

	@Override
	public void setContractCd() {
		this.mothlyPattern.setContractCd(AppContexts.user().contractCode());
	}

}
