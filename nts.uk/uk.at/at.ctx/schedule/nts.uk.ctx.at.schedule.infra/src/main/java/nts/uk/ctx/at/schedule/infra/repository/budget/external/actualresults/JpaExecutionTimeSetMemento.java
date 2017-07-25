/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresults;

import java.util.Date;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExecutionTimeSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresults.KbldtExtBudgetLog;

/**
 * The Class JpaExecutionTimeSetMemento.
 */
public class JpaExecutionTimeSetMemento implements ExecutionTimeSetMemento {
    
    /** The entity parent. */
    private KbldtExtBudgetLog entityParent;
    
    /**
     * Instantiates a new jpa execution time set memento.
     *
     * @param entityParent the entity parent
     */
    public JpaExecutionTimeSetMemento(KbldtExtBudgetLog entityParent) {
        this.entityParent = entityParent;
    }
    
    /**
     * Sets the start date.
     *
     * @param startDate the new start date
     */
    @Override
    public void setStartDate(Date startDate) {
        this.entityParent.setStrD(GeneralDate.legacyDate(startDate));
    }

    /**
     * Sets the end date.
     *
     * @param endDate the new end date
     */
    @Override
    public void setEndDate(Date endDate) {
        this.entityParent.setEndD(GeneralDate.legacyDate(endDate));
    }

}
