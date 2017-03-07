/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

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
 * The Class AccidentInsuranceRateUpdateCommandHandler.
 */
@Stateless
public class AccidentInsuranceRateUpdateCommandHandler extends CommandHandler<AccidentInsuranceRateUpdateCommand> {

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
	protected void handle(CommandHandlerContext<AccidentInsuranceRateUpdateCommand> context) {
		// getCommand
		AccidentInsuranceRateUpdateCommand command = context.getCommand();
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();
		AccidentInsuranceRate accidentInsuranceRate = command.toDomain(loginUserContext.companyCode());
		// validate domain
		accidentInsuranceRate.validate();
		// validate input
		this.accidentInsuranceRateService.validateDateRangeUpdate(accidentInsuranceRate);
		// connection service update
		this.accidentInsuranceRateRepo.update(accidentInsuranceRate);
	}

}
