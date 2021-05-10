/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error;

/**
 * The Interface ExternalBudgetErrorSetMemento.
 */
public interface ExternalBudgetErrorSetMemento {
    
    /**
     * Sets the error content.
     *
     * @param errorContent the new error content
     */
    void setErrorContent(ExtBudgetErrorContent errorContent);

    /**
     * Sets the column no.
     *
     * @param numberColumn the new column no
     */
    void setColumnNo(int numberColumn);

    /**
     * Sets the actual value.
     *
     * @param actualValue the new actual value
     */
    void setActualValue(ExtBudgetActualValue actualValue);

    /**
     * Sets the accepted date.
     *
     * @param acceptedDate the new accepted date
     */
    void setAcceptedDate(ExtBudgetAccDate acceptedDate);

    /**
     * Sets the work place code.
     *
     * @param workPlaceCode the new work place code
     */
    void setWorkPlaceCode(ExtBudgetWorkplaceCode workPlaceCode);

    /**
     * Sets the execution id.
     *
     * @param excutionId the new execution id
     */
    void setExecutionId(String executionId);

    /**
     * Sets the line no.
     *
     * @param numberLine the new line no
     */
    void setLineNo(int numberLine);
}
