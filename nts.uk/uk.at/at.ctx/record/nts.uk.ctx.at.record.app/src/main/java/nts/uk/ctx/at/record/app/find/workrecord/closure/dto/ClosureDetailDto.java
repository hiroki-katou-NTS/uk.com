/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.closure.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.closure.CloseName;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureDate;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistory;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryId;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistorySetMemento;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureId;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureMonth;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureSetMemento;
import nts.uk.ctx.at.record.dom.workrecord.closure.CompanyId;
import nts.uk.ctx.at.record.dom.workrecord.closure.UseClassification;

/**
 * The Class ClosureDetailDto.
 */

@Getter
@Setter
public class ClosureDetailDto implements ClosureHistorySetMemento, ClosureSetMemento {
	
	/** The history id. */
	private String historyId;
	
	/** The closure id. */
	private int closureId;
	
	/** The closure name. */
	private String closureName;
	
	/** The closure date. */
	private int closureDate;
	
	/** The use classification. */
	private int useClassification;

	/** The end date. */
	// 終了年月: 年月
	private int endDate;

	/** The start date. */
	// 開始年月: 年月
	private int startDate;
	
	/** The month. */
	private int month;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureSetMemento#
	 * setClosureId(java.lang.Integer)
	 */
	@Override
	public void setClosureId(Integer closureId) {
		this.closureId = closureId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureSetMemento#
	 * setUseClassification(nts.uk.ctx.at.record.dom.workrecord.closure.
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
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureSetMemento#setMonth(
	 * nts.uk.ctx.at.record.dom.workrecord.closure.ClosureMonth)
	 */
	@Override
	public void setMonth(ClosureMonth month) {
		this.month = month.getProcessingDate().v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.workrecord.closure.ClosureSetMemento#
	 * setClosureHistories(java.util.List)
	 */
	@Override
	public void setClosureHistories(List<ClosureHistory> closureHistories) {
		// No thing code

	}

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
	 * setCompanyId(nts.uk.ctx.at.record.dom.workrecord.closure.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// Do no thing code

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
		this.endDate = endDate.v();

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
			this.closureDate = 0;
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
		this.startDate = startDate.v();
	}

}
