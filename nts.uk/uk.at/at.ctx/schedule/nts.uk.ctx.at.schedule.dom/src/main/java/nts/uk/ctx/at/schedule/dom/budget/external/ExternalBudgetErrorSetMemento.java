package nts.uk.ctx.at.schedule.dom.budget.external;

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
     * Sets the number column.
     *
     * @param numberColumn the new number column
     */
    void setNumberColumn(int numberColumn);

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
     * Sets the excution id.
     *
     * @param excutionId the new excution id
     */
    void setExcutionId(String excutionId);

    /**
     * Sets the number line.
     *
     * @param numberLine the new number line
     */
    void setNumberLine(int numberLine);
}
