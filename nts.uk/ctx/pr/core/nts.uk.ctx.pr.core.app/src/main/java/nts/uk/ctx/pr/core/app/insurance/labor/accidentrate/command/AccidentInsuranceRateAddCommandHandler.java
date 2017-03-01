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

/**
 * The Class AccidentInsuranceRateAddCommandHandler.
 */
@Stateless
@Transactional
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
	protected void handle(CommandHandlerContext<AccidentInsuranceRateAddCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		AccidentInsuranceRate accidentInsuranceRate = context.getCommand().toDomain(companyCode);
		accidentInsuranceRate.validate();
		accidentInsuranceRateService.validateDateRange(accidentInsuranceRate);
		this.accidentInsuranceRateRepo.add(accidentInsuranceRate);
	}

}
