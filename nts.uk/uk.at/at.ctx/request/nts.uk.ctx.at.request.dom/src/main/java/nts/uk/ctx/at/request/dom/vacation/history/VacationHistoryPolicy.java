package nts.uk.ctx.at.request.dom.vacation.history;

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
	void validate(Boolean isCreated, PlanVacationHistory vacationHistory, Boolean isCheck);

}
