/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.at.app.kmk004.company.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew.SaveComStatWorkTimeSetCommandHandler;

/**
 * The Class Kmk004ComAddCommandHandler.
 */
@Stateless
@Transactional
public class Kmk004ComAddCommandHandler extends CommandHandler<Kmk004ComAddCommand> {

	/** The save com stat work time set command handler. */
	@Inject
	private SaveComStatWorkTimeSetCommandHandler saveCommand;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<Kmk004ComAddCommand> context) {
		// TODO Auto-generated method stub
		Kmk004ComAddCommand addCommand = context.getCommand();
		this.saveCommand.handle(addCommand.getSaveCommand());
	}

}
