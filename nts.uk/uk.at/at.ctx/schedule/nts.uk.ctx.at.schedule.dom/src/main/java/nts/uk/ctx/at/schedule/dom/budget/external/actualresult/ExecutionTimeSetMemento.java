/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult;

import java.util.Date;

/**
 * The Interface ExecutionTimeSetMemento.
 */
public interface ExecutionTimeSetMemento {
    
    /**
     * Sets the start date.
     *
     * @param startDate the new start date
     */
    void setStartDate(Date startDate);

    /**
     * Sets the end date.
     *
     * @param endDate the new end date
     */
    void setEndDate(Date endDate);
}
