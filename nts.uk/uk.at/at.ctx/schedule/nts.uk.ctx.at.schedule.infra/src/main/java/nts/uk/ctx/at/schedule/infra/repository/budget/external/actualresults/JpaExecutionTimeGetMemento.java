/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresults;

import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExecutionTimeGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.acceptance.log.KscdtExtBudgetLog;

/**
 * The Class JpaExecutionTimeGetMemento.
 */
public class JpaExecutionTimeGetMemento implements ExecutionTimeGetMemento {

    /** The entity parent. */
    private KscdtExtBudgetLog entityParent;

    /**
     * Instantiates a new jpa execution time get memento.
     *
     * @param entityParent
     *            the entity parent
     */
    public JpaExecutionTimeGetMemento(KscdtExtBudgetLog entityParent) {
        this.entityParent = entityParent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExecutionTimeGetMemento#getStartDateTime()
     */
    @Override
    public GeneralDateTime getStartDateTime() {
        return this.entityParent.getStrDateTime();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExecutionTimeGetMemento#getEndDateTime()
     */
    @Override
    public GeneralDateTime getEndDateTime() {
        return this.entityParent.getEndDateTime();
    }

}
