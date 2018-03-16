/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;


/**
 * The Class DeleteShainRegularWorkHourCommandHandler.
 */
@Stateless
public class DeleteShainRegularWorkHourCommandHandler extends CommandHandler<DeleteShainRegularWorkHourCommand> {

	/** The emp reg work hour repo. */
	//@Inject
	//private EmployeeRegularWorkHourRepository empRegWorkHourRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteShainRegularWorkHourCommand> context) {
		// TODO Auto-generated method stub

	}

}
