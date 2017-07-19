package nts.uk.ctx.at.schedule.dom.budget.external;

/**
 * The Interface ExternalBudgetTimeZoneRepository.
 */
public interface ExternalBudgetTimeZoneRepository {
    
    /**
     * Adds the.
     *
     * @param <T> the generic type
     * @param domain the domain
     */
    <T> void add(ExternalBudgetTimeZone<T> domain);
    
    /**
     * Update.
     *
     * @param <T> the generic type
     * @param domain the domain
     */
    <T> void update(ExternalBudgetTimeZone<T> domain);
}
