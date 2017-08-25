/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.log;

import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetCd;

/**
 * The Interface ExternalBudgetLogGetMemento.
 */
public interface ExternalBudgetLogGetMemento {

    /**
     * Gets the external budget file name.
     *
     * @return the external budget file name
     */
    ExtBudgetFileName getExternalBudgetFileName();

    /**
     * Gets the external budget code.
     *
     * @return the external budget code
     */
    ExternalBudgetCd getExternalBudgetCode();

    /**
     * Gets the number fail.
     *
     * @return the number fail
     */
    int getNumberFail();

    /**
     * Gets the completion state.
     *
     * @return the completion state
     */
    CompletionState getCompletionState();

    /**
     * Gets the execution id.
     *
     * @return the execution id
     */
    String getExecutionId();

    /**
     * Gets the execute time.
     *
     * @return the execute time
     */
    ExecutionTime getExecuteTime();

    /**
     * Gets the number success.
     *
     * @return the number success
     */
    int getNumberSuccess();

    /**
     * Gets the employee id.
     *
     * @return the employee id
     */
    String getEmployeeId();
}
