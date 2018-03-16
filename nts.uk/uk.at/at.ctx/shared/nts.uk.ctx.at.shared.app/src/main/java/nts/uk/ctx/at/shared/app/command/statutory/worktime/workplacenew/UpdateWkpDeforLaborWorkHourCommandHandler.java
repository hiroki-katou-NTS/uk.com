/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class UpdateWkpDeforLaborWorkHourCommandHandler.
 */
@Stateless
public class UpdateWkpDeforLaborWorkHourCommandHandler extends CommandHandler<UpdateWkpDeforLaborWorkHourCommand> {

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
	protected void handle(CommandHandlerContext<UpdateWkpDeforLaborWorkHourCommand> context) {
		// TODO Auto-generated method stub

	}

}
