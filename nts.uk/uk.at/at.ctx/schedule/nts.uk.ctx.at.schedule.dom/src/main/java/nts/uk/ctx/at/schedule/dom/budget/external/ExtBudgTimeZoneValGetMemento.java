package nts.uk.ctx.at.schedule.dom.budget.external;

/**
 * The Interface ExtBudgTimeZoneValGetMemento.
 *
 * @param <T> the generic type
 */
public interface ExtBudgTimeZoneValGetMemento<T> {

    /**
     * Gets the time period.
     *
     * @return the time period
     */
    int getTimePeriod();

    /**
     * Gets the actual value.
     *
     * @return the actual value
     */
    ExternalBudgetVal<T> getActualValue();
}
