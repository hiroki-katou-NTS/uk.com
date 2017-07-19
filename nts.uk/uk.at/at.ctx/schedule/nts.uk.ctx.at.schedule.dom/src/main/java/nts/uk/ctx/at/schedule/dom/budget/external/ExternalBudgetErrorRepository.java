package nts.uk.ctx.at.schedule.dom.budget.external;

/**
 * The Interface ExternalBudgetErrorRepository.
 */
public interface ExternalBudgetErrorRepository {

    /**
     * Adds the.
     *
     * @param domain the domain
     */
    void add(ExternalBudgetError domain);
    
    /**
     * Update.
     *
     * @param domain the domain
     */
    void update(ExternalBudgetError domain);
}
