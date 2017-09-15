/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.dto;

import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace.Period;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistorySetMemento;

/**
 * The Class WorkplaceHistoryDto.
 */
public class WorkplaceHistoryDto implements WorkplaceHistorySetMemento {

	/** The history id. */
	// 履歴ID
	public String historyId;

	/** The period. */
	// 期間
	public Period period;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistorySetMemento#setHistoryId(nts.uk.ctx.bs.employee.dom.workplace.HistoryId)
	 */
	@Override
	public void setHistoryId(HistoryId historyId) {
		this.historyId = historyId.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistorySetMemento#setPeriod(nts.uk.ctx.bs.employee.dom.workplace.Period)
	 */
	@Override
	public void setPeriod(Period period) {
		this.period = period;
	}

}
