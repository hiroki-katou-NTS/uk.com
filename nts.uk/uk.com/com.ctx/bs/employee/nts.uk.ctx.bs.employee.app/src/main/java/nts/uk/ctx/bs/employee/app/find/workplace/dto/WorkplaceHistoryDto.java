/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.dto;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistorySetMemento;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * The Class WorkplaceHistoryDto.
 */
public class WorkplaceHistoryDto implements WorkplaceHistorySetMemento {

    /** The history id. */
    // 履歴ID
    public String historyId;

    /** The start date. */
    // 期間
    public GeneralDate startDate;
    
    /** The end date. */
    public GeneralDate endDate;

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistorySetMemento#
     * setHistoryId(nts.uk.ctx.bs.employee.dom.workplace.HistoryId)
     */
    @Override
    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistorySetMemento#setPeriod
     * (nts.uk.ctx.bs.employee.dom.workplace.Period)
     */
    @Override
    public void setPeriod(DatePeriod period) {
        this.startDate = period.start();
        this.endDate = period.end();
    }

}
