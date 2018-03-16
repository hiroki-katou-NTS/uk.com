/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class UpdateShainSpeDeforLaborHourCommandHandler.
 */
@Stateless
public class UpdateShainSpeDeforLaborHourCommandHandler
		extends CommandHandler<UpdateShainSpeDeforLaborHourCommand> {

	/** The emp spe defor labor hour repo. */
	//@Inject
	//private EmployeeSpeDeforLaborHourRepository empSpeDeforLaborHourRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateShainSpeDeforLaborHourCommand> context) {
		// TODO Auto-generated method stub

	}

}
