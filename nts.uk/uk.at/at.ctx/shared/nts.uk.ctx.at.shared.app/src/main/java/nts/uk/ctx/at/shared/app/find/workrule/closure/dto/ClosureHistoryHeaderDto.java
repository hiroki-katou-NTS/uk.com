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
 * The Class ClosureHistoryHeaderDto.
 */
@Getter
@Setter
public class ClosureHistoryHeaderDto implements ClosureHistorySetMemento {

	/** The Constant LAST_DATE. */
	public static final int LAST_DATE = 0;

	/** The start date. */
	private int startDate;
	
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
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setCloseName(nts.uk.ctx.at.shared.dom.workrule.closure.CloseName)
	 */
	@Override
	public void setClosureName(ClosureName closeName) {
		this.closureName = closeName.v();
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
		this.closureId = closureId.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setEndDate(nts.arc.time.YearMonth)
	 */
	@Override
	public void setEndDate(YearMonth endDate) {
		// No thing code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setClosureDate(nts.uk.shr.com.time.calendar.date.ClosureDate)
	 */
	@Override
	public void setClosureDate(ClosureDate closureDate) {
		if(closureDate.getLastDayOfMonth()){
			this.closureDate = LAST_DATE;
		}
		else {
			this.closureDate = closureDate.getClosureDay().v();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setStartDate(nts.arc.time.YearMonth)
	 */
	@Override
	public void setStartDate(YearMonth startDate) {
		this.startDate = startDate.v();
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
		// No thing code
	}
	

}
