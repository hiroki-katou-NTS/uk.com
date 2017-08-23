/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.log;

import nts.arc.time.GeneralDateTime;

/**
 * The Interface ExecutionTimeGetMemento.
 */
public interface ExecutionTimeGetMemento {
    
    /**
     * Gets the start date.
     *
     * @return the start date
     */
    GeneralDateTime getStartDateTime();

    /**
     * Gets the end date.
     *
     * @return the end date
     */
    GeneralDateTime getEndDateTime();
}
