/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.budget.external.actualresult.error;

/**
 * The Interface ExternalBudgetErrorGetMemento.
 */
public interface ExternalBudgetErrorGetMemento {
    
    /**
     * Gets the error content.
     *
     * @return the error content
     */
    ExtBudgetErrorContent getErrorContent();

    /**
     * Gets the column no.
     *
     * @return the column no
     */
    int getColumnNo();

    /**
     * Gets the actual value.
     *
     * @return the actual value
     */
    ExtBudgetActualValue getActualValue();

    /**
     * Gets the accepted date.
     *
     * @return the accepted date
     */
    ExtBudgetAccDate getAcceptedDate();

    /**
     * Gets the work place code.
     *
     * @return the work place code
     */
    ExtBudgetWorkplaceCode getWorkPlaceCode();

    /**
     * Gets the execution id.
     *
     * @return the execution id
     */
    String getExecutionId();

    /**
     * Gets the line no.
     *
     * @return the line no
     */
    int getLineNo();
}
