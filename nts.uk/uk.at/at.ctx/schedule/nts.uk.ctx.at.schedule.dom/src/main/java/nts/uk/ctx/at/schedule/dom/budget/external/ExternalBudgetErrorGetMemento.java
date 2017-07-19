package nts.uk.ctx.at.schedule.dom.budget.external;

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
     * Gets the process date.
     *
     * @return the process date
     */
    Integer getProcessDate();

    /**
     * Gets the work type code.
     *
     * @return the work type code
     */
    String getWorkTypeCode();

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
