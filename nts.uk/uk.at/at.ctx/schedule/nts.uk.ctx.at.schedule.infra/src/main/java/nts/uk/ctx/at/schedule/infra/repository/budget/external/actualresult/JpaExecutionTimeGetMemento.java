/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult;

import java.util.Date;

import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExecutionTimeGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KbldtExtBudgetLog;

/**
 * The Class JpaExecutionTimeGetMemento.
 */
public class JpaExecutionTimeGetMemento implements ExecutionTimeGetMemento {

    /** The entity parent. */
    private KbldtExtBudgetLog entityParent;

    /**
     * Instantiates a new jpa execution time get memento.
     *
     * @param entityParent
     *            the entity parent
     */
    public JpaExecutionTimeGetMemento(KbldtExtBudgetLog entityParent) {
        this.entityParent = entityParent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresults.
     * ExecutionTimeGetMemento#getStartDate()
     */
    @Override
    public Date getStartDate() {
        return this.entityParent.getStrD().date();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresults.
     * ExecutionTimeGetMemento#getEndDate()
     */
    @Override
    public Date getEndDate() {
        return this.entityParent.getEndD().date();
    }

}
