/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.service.AccidentInsuranceRateService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class AccidentInsuranceRateAddCommandHandler.
 */
@Stateless
public class AccidentInsuranceRateAddCommandHandler extends CommandHandler<AccidentInsuranceRateAddCommand> {

	/** The accident insurance rate repository. */
	@Inject
	private AccidentInsuranceRateRepository accidentInsuranceRateRepo;

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
	protected void handle(CommandHandlerContext<AccidentInsuranceRateAddCommand> context) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get commpany user login
		String companyCode = loginUserContext.companyCode();

		// get command
		AccidentInsuranceRateAddCommand command = context.getCommand();

		// get domain by action request
		AccidentInsuranceRate accidentInsuranceRate = command.toDomain(companyCode);

		// validate domain
		accidentInsuranceRate.validate();

		// validate input domian
		accidentInsuranceRateService.validateDateRange(accidentInsuranceRate);

		// get first data
		Optional<AccidentInsuranceRate> optionalFirst = this.accidentInsuranceRateRepo
			.findFirstData(companyCode);

		if (optionalFirst.isPresent()) {
			this.accidentInsuranceRateRepo.updateYearMonth(optionalFirst.get(),
				accidentInsuranceRate.getApplyRange().getStartMonth().previousMonth());
		}

		// connection repository running add
		this.accidentInsuranceRateRepo.add(accidentInsuranceRate);
	}

}
