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

/**
 * The Class AccidentInsuranceRateAddCommandHandler.
 */
@Stateless
@Transactional
public class AccidentInsuranceRateAddCommandHandler extends CommandHandler<AccidentInsuranceRateAddCommand> {

	/** The accident insurance rate repository. */
	@Inject
	private AccidentInsuranceRateRepository accidentInsuranceRateRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AccidentInsuranceRateAddCommand> context) {
		AccidentInsuranceRate accidentInsuranceRate = context.getCommand().toDomain();
		accidentInsuranceRate.validate();
		this.accidentInsuranceRateRepo.add(accidentInsuranceRate);
	}

}
