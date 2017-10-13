/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.config.dto;

import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistorySetMemento;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class WorkplaceConfigHistoryDto.
 */
public class WorkplaceConfigHistoryDto implements WorkplaceConfigHistorySetMemento {

    /** The history id. */
    // ID
    public String historyId;

	/** The period. */
	// 期間
	public DatePeriod period;

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.config.
     * WorkplaceConfigHistorySetMemento#setHistoryId(java.lang.String)
     */
    @Override
    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.config.
     * WorkplaceConfigHistorySetMemento#setPeriod(nts.uk.shr.com.time.calendar.
     * period.DatePeriod)
     */
	@Override
	public void setPeriod(DatePeriod period) {
		this.period = period;
	}
}
