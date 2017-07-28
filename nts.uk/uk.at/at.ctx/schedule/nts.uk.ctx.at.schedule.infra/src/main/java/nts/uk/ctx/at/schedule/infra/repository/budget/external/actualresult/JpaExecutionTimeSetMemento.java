/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult;

import java.util.Date;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExecutionTimeSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KscdtExtBudgetLog;

/**
 * The Class JpaExecutionTimeSetMemento.
 */
public class JpaExecutionTimeSetMemento implements ExecutionTimeSetMemento {
    
    /** The entity parent. */
    private KscdtExtBudgetLog entityParent;
    
    /**
     * Instantiates a new jpa execution time set memento.
     *
     * @param entityParent the entity parent
     */
    public JpaExecutionTimeSetMemento(KscdtExtBudgetLog entityParent) {
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
