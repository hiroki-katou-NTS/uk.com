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
     * Sets the process date.
     *
     * @param processDate the new process date
     */
    void setProcessDate(Integer processDate);

    /**
     * Sets the work type code.
     *
     * @param workTypeCode the new work type code
     */
    void setWorkTypeCode(String workTypeCode);

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
