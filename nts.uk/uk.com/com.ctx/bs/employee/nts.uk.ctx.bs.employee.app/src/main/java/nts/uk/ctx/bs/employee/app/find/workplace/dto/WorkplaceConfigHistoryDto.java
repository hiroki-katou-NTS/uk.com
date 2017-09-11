/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.dto;

import nts.uk.ctx.bs.employee.dom.workplace.Period;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceConfigHistorySetMemento;

public class WorkplaceConfigHistoryDto implements WorkplaceConfigHistorySetMemento {

	/** The history id. */
	// ID
	public String historyId;

	/** The period. */
	// 期間
	public Period period;

	@Override
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	@Override
	public void setPeriod(Period period) {
		this.period = period;
	}
}
