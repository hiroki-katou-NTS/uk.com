/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class SaveCompanyTransLaborHourCommandHandler.
 */
@Stateless
public class SaveCompanyTransLaborHourCommandHandler extends CommandHandler<SaveComTransLaborHourCommand> {

	/** The Company trans labor hour repository. */
	//@Inject
	//private CompanyTransLaborHourRepository CompanyTransLaborHourRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveComTransLaborHourCommand> context) {
		// TODO Auto-generated method stub

	}

}
