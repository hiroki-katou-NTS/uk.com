/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.CompletionState;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExecutionTime;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExtBudgetFileName;
import nts.uk.ctx.at.schedule.dom.budget.external.actualresult.ExternalBudgetLogSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.KbldtExtBudgetLog;

/**
 * The Class JpaExternalBudgetLogSetMemento.
 */
public class JpaExternalBudgetLogSetMemento implements ExternalBudgetLogSetMemento {

    /** The entity. */
    private KbldtExtBudgetLog entity;

    /**
     * Instantiates a new jpa external budget log set memento.
     *
     * @param entity
     *            the entity
     */
    public JpaExternalBudgetLogSetMemento(KbldtExtBudgetLog entity) {
        this.entity = entity;
    }

    /**
     * Sets the company id.
     *
     * @param companyId
     *            the new company id
     */
    @Override
    public void setCompanyId(String companyId) {
        this.entity.setCid(companyId);
    }

    /**
     * Sets the external budget file name.
     *
     * @param externalBudgetFileName
     *            the new external budget file name
     */
    @Override
    public void setExternalBudgetFileName(ExtBudgetFileName externalBudgetFileName) {
        this.entity.setFileName(externalBudgetFileName.v());
    }

    /**
     * Sets the external budget code.
     *
     * @param externalBudgetCode
     *            the new external budget code
     */
    @Override
    public void setExternalBudgetCode(ExternalBudgetCd externalBudgetCode) {
        this.entity.setExtBudgetCd(externalBudgetCode.v());
    }

    /**
     * Sets the number fail.
     *
     * @param numberFail
     *            the new number fail
     */
    @Override
    public void setNumberFail(int numberFail) {
        this.entity.setFailureCnt(numberFail);
    }

    /**
     * Sets the completion state.
     *
     * @param completionState
     *            the new completion state
     */
    @Override
    public void setCompletionState(CompletionState completionState) {
        this.entity.setCompletionAtr(completionState.value);
    }

    /**
     * Sets the execution id.
     *
     * @param executionId
     *            the new execution id
     */
    @Override
    public void setExecutionId(String executionId) {
        this.entity.setExeId(executionId);
    }

    /**
     * Sets the execute time.
     *
     * @param executeTime
     *            the new execute time
     */
    @Override
    public void setExecuteTime(ExecutionTime executeTime) {
        JpaExecutionTimeSetMemento memento = new JpaExecutionTimeSetMemento(this.entity);
        executeTime.saveToMemento(memento);
    }

    /**
     * Sets the number success.
     *
     * @param numberSuccess
     *            the new number success
     */
    @Override
    public void setNumberSuccess(int numberSuccess) {
        this.entity.setSuccessCnt(numberSuccess);
    }

    /**
     * Sets the employee id.
     *
     * @param employeeId
     *            the new employee id
     */
    @Override
    public void setEmployeeId(String employeeId) {
        this.entity.setSid(employeeId);
    }

}
