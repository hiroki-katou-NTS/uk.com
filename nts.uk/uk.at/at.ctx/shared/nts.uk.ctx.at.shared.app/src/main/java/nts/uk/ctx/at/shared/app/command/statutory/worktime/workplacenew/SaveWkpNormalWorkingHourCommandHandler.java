/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class SaveWkpNormalWorkingHourCommandHandler.
 */
@Stateless
public class SaveWkpNormalWorkingHourCommandHandler extends CommandHandler<SaveWkpNormalWorkingHourCommand> {

	/** The wkp normal working hour repo. */
	//@Inject
	//private WkpNormalWorkingHourRepository wkpNormalWorkingHourRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveWkpNormalWorkingHourCommand> context) {
		// TODO Auto-generated method stub

	}

}
