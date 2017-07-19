package nts.uk.ctx.at.schedule.dom.budget.external;

/**
 * The Interface ExternalBudgetDailyRepository.
 */
public interface ExternalBudgetDailyRepository {
    
    /**
     * Adds the.
     *
     * @param <T> the generic type
     * @param domain the domain
     */
    <T> void add(ExternalBudgetDaily<T> domain);
    
    /**
     * Update.
     *
     * @param <T> the generic type
     * @param domain the domain
     */
    <T>void update(ExternalBudgetDaily<T> domain);
}
