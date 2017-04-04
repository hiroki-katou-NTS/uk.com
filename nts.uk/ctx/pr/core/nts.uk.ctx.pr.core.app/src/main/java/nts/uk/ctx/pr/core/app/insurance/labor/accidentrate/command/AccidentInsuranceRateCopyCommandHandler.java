/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

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

/**
 * The Class AccidentInsuranceRateCopyCommandHandler.
 */
@Stateless
public class AccidentInsuranceRateCopyCommandHandler
		extends CommandHandler<AccidentInsuranceRateCopyCommand> {

	/** The accident insurance rate repo. */
	@Inject
	private AccidentInsuranceRateRepository rateRepo;

	/** The accident insurance rate service. */
	@Inject
	private AccidentInsuranceRateService rateService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<AccidentInsuranceRateCopyCommand> context) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company code user login
		String companyCode = loginUserContext.companyCode();

		// get command
		AccidentInsuranceRateCopyCommand command = context.getCommand();

		// get domain by action request
		AccidentInsuranceRate accidentInsuranceRate = AccidentInsuranceRate
				.createWithIntial(companyCode, YearMonth.of(command.getStartMonth()));

		if (!command.isAddNew() && command.getHistoryIdCopy() != null
				&& command.getHistoryIdCopy().length() > 0) {

			// add new with start historyId
			Optional<AccidentInsuranceRate> optionalFindAdd = this.rateRepo
					.findById(companyCode, command.getHistoryIdCopy());

			// Check exist.
			if (optionalFindAdd.isPresent()) {
				accidentInsuranceRate = optionalFindAdd.get();
				accidentInsuranceRate = accidentInsuranceRate
						.copyWithDate(YearMonth.of(command.getStartMonth()));
			}
		}
		// validate domain
		accidentInsuranceRate.setMaxDate();
		accidentInsuranceRate.validate();

		// validate input domain
		rateService.validateDateRange(accidentInsuranceRate);

		// get first data
		Optional<AccidentInsuranceRate> optionalFirst = this.rateRepo
				.findFirstData(companyCode);

		// Check exist
		if (optionalFirst.isPresent()) {
			optionalFirst.get()
					.setEnd(accidentInsuranceRate.getApplyRange().getStartMonth().previousMonth());
			this.rateRepo.update(optionalFirst.get());
		}

		// connection repository running add
		this.rateRepo.add(accidentInsuranceRate);
	}

}
