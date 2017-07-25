/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresults;

import java.util.Date;

import nts.uk.ctx.at.schedule.dom.budget.external.actualresults.ExecutionTimeGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresults.KbldtExtBudgetLog;

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
