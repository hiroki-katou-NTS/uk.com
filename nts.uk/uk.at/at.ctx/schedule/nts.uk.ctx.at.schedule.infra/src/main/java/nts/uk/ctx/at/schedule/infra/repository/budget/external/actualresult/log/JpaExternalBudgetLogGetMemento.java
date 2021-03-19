/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult.log;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.CompletionState;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExecutionTime;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExtBudgetFileName;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log.ExternalBudgetLogGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.log.KscdtExtBudgetLog;
import nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult.timeunit.JpaExecutionTimeGetMemento;

/**
 * The Class JpaExternalBudgetLogGetMemento.
 */
public class JpaExternalBudgetLogGetMemento implements ExternalBudgetLogGetMemento {

    /** The entity. */
    private KscdtExtBudgetLog entity;

    /**
     * Instantiates a new jpa external budget log get memento.
     *
     * @param entity
     *            the entity
     */
    public JpaExternalBudgetLogGetMemento(KscdtExtBudgetLog entity) {
        this.entity = entity;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresults.
     * ExternalBudgetLogGetMemento#getExternalBudgetFileName()
     */
    @Override
    public ExtBudgetFileName getExternalBudgetFileName() {
        return new ExtBudgetFileName(this.entity.getFileName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresults.
     * ExternalBudgetLogGetMemento#getExternalBudgetCode()
     */
    @Override
    public ExternalBudgetCd getExternalBudgetCode() {
        return new ExternalBudgetCd(this.entity.getExtBudgetCd());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresults.
     * ExternalBudgetLogGetMemento#getNumberFail()
     */
    @Override
    public int getNumberFail() {
        return this.entity.getFailureCnt();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresults.
     * ExternalBudgetLogGetMemento#getCompletionState()
     */
    @Override
    public CompletionState getCompletionState() {
        return CompletionState.valueOf(this.entity.getCompletionAtr());
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresults.
     * ExternalBudgetLogGetMemento#getExecutionId()
     */
    @Override
    public String getExecutionId() {
        return this.entity.getExeId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresults.
     * ExternalBudgetLogGetMemento#getExecuteTime()
     */
    @Override
    public ExecutionTime getExecuteTime() {
        return new ExecutionTime(new JpaExecutionTimeGetMemento(this.entity));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresults.
     * ExternalBudgetLogGetMemento#getNumberSuccess()
     */
    @Override
    public int getNumberSuccess() {
        return this.entity.getSuccessCnt();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresults.
     * ExternalBudgetLogGetMemento#getEmployeeId()
     */
    @Override
    public String getEmployeeId() {
        return this.entity.getSid();
    }

}
