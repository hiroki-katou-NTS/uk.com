package nts.uk.ctx.at.schedule.dom.budget.external.actualresults;

import nts.uk.ctx.at.schedule.dom.budget.external.ExtBudgetWorkplaceCode;

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
     * Gets the number column.
     *
     * @return the number column
     */
    int getNumberColumn();

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
     * Gets the excution id.
     *
     * @return the excution id
     */
    String getExcutionId();

    /**
     * Gets the number line.
     *
     * @return the number line
     */
    int getNumberLine();
}
