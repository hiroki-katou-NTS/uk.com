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
 * The Class ClosureHistoryHeaderDto.
 */
@Getter
@Setter
public class ClosureHistoryHeaderDto implements ClosureHistorySetMemento {

	/** The Constant LAST_DATE. */
	public static final int LAST_DATE = 0;

	/** The history id. */
	private String historyId;
	
	/** The closure id. */
	private int closureId;
	
	/** The closure name. */
	private String closureName;
	
	/** The closure date. */
	private int closureDate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento#
	 * setCloseName(nts.uk.ctx.at.record.dom.workrecord.closure.CloseName)
	 */
	@Override
	public void setCloseName(CloseName closeName) {
		this.closureName = closeName.v();
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
		this.closureId = closureId.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento#
	 * setClosureHistoryId(nts.uk.ctx.at.record.dom.workrecord.closure.
	 * ClosureHistoryId)
	 */
	@Override
	public void setClosureHistoryId(ClosureHistoryId closureHistoryId) {
		this.historyId = closureHistoryId.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento#
	 * setEndDate(nts.arc.time.YearMonth)
	 */
	@Override
	public void setEndDate(YearMonth endDate) {
		// No thing code
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
		if(closureDate.getLastDayOfMonth()){
			this.closureDate = LAST_DATE;
		}
		else {
			this.closureDate = closureDate.getDay();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento#
	 * setStartDate(nts.arc.time.YearMonth)
	 */
	@Override
	public void setStartDate(YearMonth startDate) {
		// No thing code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento#
	 * setCompanyId(nts.uk.ctx.at.record.dom.workrecord.closure.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// No thing code
	}
	

}
