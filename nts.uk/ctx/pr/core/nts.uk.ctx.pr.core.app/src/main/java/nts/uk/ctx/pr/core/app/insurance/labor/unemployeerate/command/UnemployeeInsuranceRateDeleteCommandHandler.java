/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.DateTimeConstraints;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class UnemployeeInsuranceRateDeleteCommandHandler extends CommandHandler<UnemployeeInsuranceRateDeleteCommand> {

	/** The Constant YEAR_MONTH_MAX. */
	public static final YearMonth YEAR_MONTH_MAX = YearMonth.of(DateTimeConstraints.LIMIT_YEAR.max(),
			DateTimeConstraints.LIMIT_MONTH.max());

	/** CompanyRepository */
	@Inject
	private UnemployeeInsuranceRateRepository unemployeeInsuranceRateRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UnemployeeInsuranceRateDeleteCommand> context) {

		// get user login info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyCode by user login
		String companyCode = loginUserContext.companyCode();

		// get command
		UnemployeeInsuranceRateDeleteCommand command = context.getCommand();

		// get first data (remove)
		Optional<UnemployeeInsuranceRate> optionalRemove = this.unemployeeInsuranceRateRepository
				.findFirstData(companyCode);

		// remove data
		if (optionalRemove.isPresent() && optionalRemove.get().getHistoryId().equals(command.getCode())) {
			this.unemployeeInsuranceRateRepository.remove(companyCode, command.getCode(), command.getVersion());
			Optional<UnemployeeInsuranceRate> optionalUpdate = this.unemployeeInsuranceRateRepository
					.findFirstData(companyCode);

			// update second data
			if (optionalUpdate.isPresent()) {
				this.unemployeeInsuranceRateRepository.updateYearMonth(optionalUpdate.get(), YEAR_MONTH_MAX);
			}
		}
	}

}
