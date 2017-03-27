/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.history.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.service.UnemployeeInsuranceRateService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class UnemployeeInsuranceHistoryUpdateCommandHandler
		extends CommandHandler<UnemployeeInsuranceHistoryUpdateCommand> {

	/** CompanyRepository */
	@Inject
	private UnemployeeInsuranceRateRepository unemployeeInsuranceRateRepository;

	/** The unemployee insurance rate service. */
	@Inject
	private UnemployeeInsuranceRateService unemployeeInsuranceRateService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UnemployeeInsuranceHistoryUpdateCommand> context) {

		// get user login info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyCode by user login
		String companyCode = loginUserContext.companyCode();

		// get command
		UnemployeeInsuranceHistoryUpdateCommand command = context.getCommand();

		Optional<UnemployeeInsuranceRate> optionalUpdate = this.unemployeeInsuranceRateRepository.findById(companyCode,
				command.getHistoryId());

		// exist data
		if (optionalUpdate.isPresent()) {
			UnemployeeInsuranceRate unemployeeInsuranceRate = optionalUpdate.get();
			unemployeeInsuranceRate.setStart(YearMonth.of(command.getStartMonth()));
			unemployeeInsuranceRate.setEnd(YearMonth.of(command.getEndMonth()));

			// validate
			unemployeeInsuranceRate.validate();
			unemployeeInsuranceRateService.validateDateRangeUpdate(unemployeeInsuranceRate);

			// call get by id
			Optional<UnemployeeInsuranceRate> optionalFirst;
			optionalFirst = this.unemployeeInsuranceRateRepository
					.findById(unemployeeInsuranceRate.getCompanyCode().v(), unemployeeInsuranceRate.getHistoryId());

			// get <= start
			if (optionalFirst.isPresent()) {
				Optional<UnemployeeInsuranceRate> optionalBetweenUpdate = this.unemployeeInsuranceRateRepository
						.findBetweenUpdate(unemployeeInsuranceRate.getCompanyCode().v(),
								optionalFirst.get().getApplyRange().getStartMonth(),
								optionalFirst.get().getHistoryId());

				// update end year month start previous
				if (optionalBetweenUpdate.isPresent()) {
					this.unemployeeInsuranceRateRepository.updateYearMonth(optionalBetweenUpdate.get(),
							unemployeeInsuranceRate.getApplyRange().getStartMonth().previousMonth());
				}

				// update value
				this.unemployeeInsuranceRateRepository.update(unemployeeInsuranceRate);
			}
		}
	}

}
