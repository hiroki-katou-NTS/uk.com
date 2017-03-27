/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.history.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.service.AccidentInsuranceRateService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class AccidentInsuranceHistoryUpdateCommandHandler
extends CommandHandler<AccidentInsuranceHistoryUpdateCommand> {

	/** The accident insurance rate repository. */
	@Inject
	private AccidentInsuranceRateRepository accidentInsuranceRateRepository;

	/** The accident insurance rate service. */
	@Inject
	private AccidentInsuranceRateService accidentInsuranceRateService;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<AccidentInsuranceHistoryUpdateCommand> context) {

		// get user login info
		LoginUserContext loginUserContext = AppContexts.user();

		// get companyCode by user login
		String companyCode = loginUserContext.companyCode();

		// get command
		AccidentInsuranceHistoryUpdateCommand command = context.getCommand();

		Optional<AccidentInsuranceRate> optionalUpdate = this.accidentInsuranceRateRepository.findById(companyCode,
				command.getHistoryId());

		// exist data
		if (optionalUpdate.isPresent()) {
			AccidentInsuranceRate accidentInsuranceRate = optionalUpdate.get();
			accidentInsuranceRate.setStart(YearMonth.of(command.getStartMonth()));
			accidentInsuranceRate.setEnd(YearMonth.of(command.getEndMonth()));

			// validate
			accidentInsuranceRate.validate();
			this.accidentInsuranceRateService.validateDateRangeUpdate(accidentInsuranceRate);

			// call get by id
			Optional<AccidentInsuranceRate> optionalFirst;
			optionalFirst = this.accidentInsuranceRateRepository
					.findById(accidentInsuranceRate.getCompanyCode().v(), accidentInsuranceRate.getHistoryId());

			// get <= start
			if (optionalFirst.isPresent()) {
				Optional<AccidentInsuranceRate> optionalBetweenUpdate = this.accidentInsuranceRateRepository
						.findBetweenUpdate(accidentInsuranceRate.getCompanyCode().v(),
								optionalFirst.get().getApplyRange().getStartMonth(),
								optionalFirst.get().getHistoryId());

				// update end year month start previous
				if (optionalBetweenUpdate.isPresent()) {
					this.accidentInsuranceRateRepository.updateYearMonth(optionalBetweenUpdate.get(),
							accidentInsuranceRate.getApplyRange().getStartMonth().previousMonth());
				}

				// update value
				this.accidentInsuranceRateRepository.update(accidentInsuranceRate);
			}
		}
	}

}
