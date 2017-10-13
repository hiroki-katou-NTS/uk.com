/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.config.dto;

import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistorySetMemento;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public class WorkplaceConfigHistoryDto implements WorkplaceConfigHistorySetMemento {

	/** The history id. */
	// ID
	public String historyId;

	/** The period. */
	// 期間
	public DatePeriod period;

	@Override
	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	@Override
	public void setPeriod(DatePeriod period) {
		this.period = period;
	}
}
