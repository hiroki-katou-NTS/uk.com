/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureName;
import nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * The Class ClosureHistoryFindDto.
 */
@Getter
@Setter
public class ClosureHistoryFindDto implements ClosureHistorySetMemento{
	
	/** The id. */
	private Integer id;
	
	/** The name. */
	private String name;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setCloseName(nts.uk.ctx.at.shared.dom.workrule.closure.CloseName)
	 */
	@Override
	public void setClosureName(ClosureName closeName) {
		this.name = closeName.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setClosureId(nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId)
	 */
	@Override
	public void setClosureId(ClosureId closureId) {
		this.id = closureId.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setEndDate(nts.uk.ctx.at.shared.dom.workrule.closure.ClosureYearMonth)
	 */
	@Override
	public void setEndDate(YearMonth endDate) {
		// Do nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setClosureDate(nts.uk.shr.com.time.calendar.date.ClosureDate)
	 */
	@Override
	public void setClosureDate(ClosureDate closureDate) {
		// Do nothing code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setStartDate(nts.uk.ctx.at.shared.dom.workrule.closure.
	 * ClosureYearMonth)
	 */
	@Override
	public void setStartDate(YearMonth startDate) {
		// Do nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// Do nothing code

	}

}
