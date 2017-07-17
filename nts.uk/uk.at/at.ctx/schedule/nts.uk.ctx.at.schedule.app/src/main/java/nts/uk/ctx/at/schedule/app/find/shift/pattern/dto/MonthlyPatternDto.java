/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternName;
import nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class MonthlyPatternDto.
 */

/**
 * Gets the name.
 *
 * @return the name
 */
@Getter

/**
 * Sets the name.
 *
 * @param name the new name
 */
@Setter
public class MonthlyPatternDto implements MonthlyPatternSetMemento{
	
	/** The code. */
	private String code;
	
	/** The name. */
	private String name;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.MonthlyPatternSetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// No thing code

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
		this.code = monthlyPatternCode.v();
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
		this.name = monthlyPatternName.v();
	}

}
