/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.acceptance.log;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;

/**
 * The Interface ExternalBudgetLogSetMemento.
 */
public interface ExternalBudgetLogSetMemento {

    /**
     * Sets the external budget file name.
     *
     * @param externalBudgetFileName the new external budget file name
     */
    void setExternalBudgetFileName(ExtBudgetFileName externalBudgetFileName);

    /**
     * Sets the external budget code.
     *
     * @param externalBudgetCode the new external budget code
     */
    void setExternalBudgetCode(ExternalBudgetCd externalBudgetCode);

    /**
     * Sets the number fail.
     *
     * @param numberFail the new number fail
     */
    void setNumberFail(int numberFail);

    /**
     * Sets the completion state.
     *
     * @param completionState the new completion state
     */
    void setCompletionState(CompletionState completionState);

    /**
     * Sets the execution id.
     *
     * @param executionId the new execution id
     */
    void setExecutionId(String executionId);

    /**
     * Sets the execute time.
     *
     * @param executeTime the new execute time
     */
    void setExecuteTime(ExecutionTime executeTime);

    /**
     * Sets the number success.
     *
     * @param numberSuccess the new number success
     */
    void setNumberSuccess(int numberSuccess);

    /**
     * Sets the employee id.
     *
     * @param employeeId the new employee id
     */
    void setEmployeeId(String employeeId);
}
