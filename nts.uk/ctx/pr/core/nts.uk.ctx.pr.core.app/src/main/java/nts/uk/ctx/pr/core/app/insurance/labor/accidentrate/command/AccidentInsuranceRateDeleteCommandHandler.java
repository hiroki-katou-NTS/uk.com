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
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class AccidentInsuranceRateAddCommandHandler.
 */
@Stateless
public class AccidentInsuranceRateDeleteCommandHandler extends CommandHandler<AccidentInsuranceRateDeleteCommand> {

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
	@Transactional
	protected void handle(CommandHandlerContext<AccidentInsuranceRateDeleteCommand> context) {
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();
		// get companyCode by user login
		String companyCode = loginUserContext.companyCode();
		// getCommand
		AccidentInsuranceRateDeleteCommand command = context.getCommand();
		// call repository remove (delete database)
		this.accidentInsuranceRateRepo.remove(new CompanyCode(companyCode),
				command.getAccidentInsuranceRateDeleteDto().getCode(),
				command.getAccidentInsuranceRateDeleteDto().getVersion());
	}

}
