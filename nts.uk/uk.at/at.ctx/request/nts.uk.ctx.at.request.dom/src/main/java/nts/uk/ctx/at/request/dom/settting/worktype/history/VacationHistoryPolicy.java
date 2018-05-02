package nts.uk.ctx.at.request.dom.settting.worktype.history;

/**
 * The Interface VacationHistoryPolicy.
 */
public interface VacationHistoryPolicy {
	
	/**
	 * Validate.
	 *
	 * @param isCreated the is created
	 * @param vacationHistory the vacation history
	 */
	void validate(Boolean isCreated, PlanVacationHistory vacationHistory);

}
