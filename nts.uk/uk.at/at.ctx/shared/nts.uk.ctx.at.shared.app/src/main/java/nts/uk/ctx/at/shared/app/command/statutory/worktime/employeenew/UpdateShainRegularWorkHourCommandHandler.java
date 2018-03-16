/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;


/**
 * The Class UpdateShainRegularWorkHourCommandHandler.
 */
@Stateless
public class UpdateShainRegularWorkHourCommandHandler extends CommandHandler<UpdateShainRegularWorkHourCommand> {

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
	protected void handle(CommandHandlerContext<UpdateShainRegularWorkHourCommand> context) {
		// TODO Auto-generated method stub

	}

}
