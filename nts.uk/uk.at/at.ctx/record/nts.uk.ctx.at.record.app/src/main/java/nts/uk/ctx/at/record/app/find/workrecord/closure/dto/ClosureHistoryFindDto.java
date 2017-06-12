/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.closure.dto;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.closure.CloseName;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureDate;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryId;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureId;
import nts.uk.ctx.at.record.dom.workrecord.closure.CompanyId;

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
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento#
	 * setCloseName(nts.uk.ctx.at.record.dom.workrecord.closure.CloseName)
	 */
	@Override
	public void setCloseName(CloseName closeName) {
		this.name = closeName.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento#
	 * setClosureId(nts.uk.ctx.at.record.dom.workrecord.closure.ClosureId)
	 */
	@Override
	public void setClosureId(ClosureId closureId) {
		this.id = closureId.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento#
	 * setEndDate(nts.uk.ctx.at.record.dom.workrecord.closure.ClosureYearMonth)
	 */
	@Override
	public void setEndDate(YearMonth endDate) {
		// Do nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento#
	 * setClosureDate(nts.uk.ctx.at.record.dom.workrecord.closure.ClosureDate)
	 */
	@Override
	public void setClosureDate(ClosureDate closureDate) {
		// Do nothing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento#
	 * setStartDate(nts.uk.ctx.at.record.dom.workrecord.closure.
	 * ClosureYearMonth)
	 */
	@Override
	public void setStartDate(YearMonth startDate) {
		// Do nothing code

	}

	@Override
	public void setClosureHistoryId(ClosureHistoryId closureHistoryId) {
		// Do nothing code
	}

	@Override
	public void setCompanyId(CompanyId companyId) {
		// Do nothing code
		
	}

}
