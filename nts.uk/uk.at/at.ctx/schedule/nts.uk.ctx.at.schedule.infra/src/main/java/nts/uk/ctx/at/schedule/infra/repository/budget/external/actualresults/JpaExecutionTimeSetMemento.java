/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresults;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExecutionTimeSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.acceptance.log.KscdtExtBudgetLog;

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
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExecutionTimeSetMemento#setStartDateTime(nts.arc.time.GeneralDateTime)
     */
    @Override
    public void setStartDateTime(GeneralDateTime startDateTime) {
        this.entityParent.setStrDateTime(startDateTime);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExecutionTimeSetMemento#setEndDateTime(nts.arc.time.GeneralDateTime)
     */
    @Override
    public void setEndDateTime(GeneralDateTime endDateTime) {
        this.entityParent.setEndDateTime(endDateTime);
    }

}
