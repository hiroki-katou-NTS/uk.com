/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class SaveCompanyRegularLaborHourCommandHandler.
 */
@Stateless
public class SaveComRegularLaborHourCommandHandler extends CommandHandler<SaveComRegularLaborHourCommand> {

	/** The com reg labor hour repo. */
	//@Inject
	//private CompanyRegularLaborHourRepository comRegLaborHourRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveComRegularLaborHourCommand> context) {

	}

}
