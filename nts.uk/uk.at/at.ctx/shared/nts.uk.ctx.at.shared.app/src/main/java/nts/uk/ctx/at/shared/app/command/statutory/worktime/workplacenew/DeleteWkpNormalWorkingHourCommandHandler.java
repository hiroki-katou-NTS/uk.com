/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteWkpNormalWorkingHourCommandHandler.
 */
@Stateless
public class DeleteWkpNormalWorkingHourCommandHandler extends CommandHandler<DeleteWkpNormalWorkingHourCommand> {

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
	protected void handle(CommandHandlerContext<DeleteWkpNormalWorkingHourCommand> context) {
		// TODO Auto-generated method stub

	}

}
