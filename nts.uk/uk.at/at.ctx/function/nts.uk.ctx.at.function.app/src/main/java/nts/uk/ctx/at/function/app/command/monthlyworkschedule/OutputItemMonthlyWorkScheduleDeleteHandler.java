package nts.uk.ctx.at.function.app.command.monthlyworkschedule;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.monthlyworkschedule.OutputItemMonthlyWorkScheduleRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class OutputItemMonthlyWorkScheduleDeleteHandler.
 */
@Stateless
public class OutputItemMonthlyWorkScheduleDeleteHandler {

	/** The repository. */
	@Inject
	OutputItemMonthlyWorkScheduleRepository repository;

	/**
	 * Delete.
	 *
	 * @param code
	 *            the code
	 */
	public void delete(String code) {
		String companyId = AppContexts.user().companyId();
		repository.deleteByCidAndCode(companyId, code);
	}
}
