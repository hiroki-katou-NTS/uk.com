/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteWkpDeforLaborWorkHourCommandHandler.
 */
@Stateless
public class DeleteWkpDeforLaborWorkHourCommandHandler extends CommandHandler<DeleteWkpDeforLaborWorkHourCommand> {

	/** The wkp defor labor work hour repo. */
	//@Inject
	//private WkpDeforLaborWorkHourRepository wkpDeforLaborWorkHourRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteWkpDeforLaborWorkHourCommand> context) {
		// TODO Auto-generated method stub

	}

}
