/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log;

import nts.arc.time.GeneralDateTime;

/**
 * The Interface ExecutionTimeSetMemento.
 */
public interface ExecutionTimeSetMemento {
    
    /**
     * Sets the start date.
     *
     * @param startDateTime the new start date
     */
    void setStartDateTime(GeneralDateTime startDateTime);

    /**
     * Sets the end date.
     *
     * @param endDateTime the new end date
     */
    void setEndDateTime(GeneralDateTime endDateTime);
}
