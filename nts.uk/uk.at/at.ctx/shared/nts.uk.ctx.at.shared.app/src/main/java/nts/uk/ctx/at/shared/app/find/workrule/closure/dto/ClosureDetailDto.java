/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workrule.closure.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureName;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento;
import nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId;
import nts.uk.ctx.at.shared.dom.workrule.closure.CurrentMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * The Class ClosureDetailDto.
 */
@Getter
@Setter
public class ClosureDetailDto implements ClosureHistorySetMemento, ClosureSetMemento {

	/** The closure id. */
	private int closureId;

	/** The closure name. */
	private String closureName;

	/** The closure date. */
	private int closureDate;

	/** The use classification. */
	private int useClassification;

	/** The end date. */
	private int endDate;

	/** The start date. */
	private int startDate;

	/** The month. */
	private int month;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#
	 * setUseClassification(nts.uk.ctx.at.shared.dom.workrule.closure.
	 * UseClassification)
	 */
	@Override
	public void setUseClassification(UseClassification useClassification) {
		this.useClassification = useClassification.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#setMonth(
	 * nts.uk.ctx.at.shared.dom.workrule.closure.ClosureMonth)
	 */
	@Override
	public void setClosureMonth(CurrentMonth month) {
		this.month = month.getProcessingYm().v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureSetMemento#
	 * setClosureHistories(java.util.List)
	 */
	@Override
	public void setClosureHistories(List<ClosureHistory> closureHistories) {
		// No thing code

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setCloseName(nts.uk.ctx.at.shared.dom.workrule.closure.CloseName)
	 */
	@Override
	public void setClosureName(ClosureName closeName) {
		this.closureName = closeName.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setClosureId(nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId)
	 */
	@Override
	public void setClosureId(ClosureId closureId) {
		this.closureId = closureId.value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setCompanyId(nts.uk.ctx.at.shared.dom.workrule.closure.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// Do no thing code
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setEndDate(nts.arc.time.YearMonth)
	 */
	@Override
	public void setEndDate(YearMonth endDate) {
		this.endDate = endDate.v();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setClosureDate(nts.uk.shr.com.time.calendar.date.ClosureDate)
	 */
	@Override
	public void setClosureDate(ClosureDate closureDate) {
		if (closureDate.getLastDayOfMonth()) {
			this.closureDate = 0;
			return;
		}
		this.closureDate = closureDate.getClosureDay().v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistorySetMemento#
	 * setStartDate(nts.arc.time.YearMonth)
	 */
	@Override
	public void setStartDate(YearMonth startDate) {
		this.startDate = startDate.v();
	}

}
