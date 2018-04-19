package nts.uk.ctx.at.request.dom.settting.worktype.history;

import nts.arc.error.BundledBusinessException;

/**
 * The Interface VacationHistoryPolicy.
 */
public interface VacationHistoryPolicy {
	
	/**
	 * Validate.
	 *
	 * @param be the be
	 * @param isCreated the is created
	 * @param vacationHistory the vacation history
	 */
	void validate(BundledBusinessException be, Boolean isCreated, PlanVacationHistory vacationHistory);

}
