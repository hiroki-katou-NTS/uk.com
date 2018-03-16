/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class SaveEmploymentRegularWorkHourCommandHandler.
 */
@Stateless
public class SaveEmpRegularWorkHourCommandHandler extends CommandHandler<SaveEmpRegularWorkHourCommand> {

	/** The empl reg work hour repo. */
	//@Inject
	//private EmploymentRegularWorkHourRepository emplRegWorkHourRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveEmpRegularWorkHourCommand> context) {
		// TODO Auto-generated method stub

	}

}
