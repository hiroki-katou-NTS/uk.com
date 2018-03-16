/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employee;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class UpdateEmployeeLaborDeforSetTemporaryCommandHandler.
 */
@Stateless
public class UpdateEmployeeLaborDeforSetTemporaryCommandHandler
		extends CommandHandler<UpdateEmployeeLaborDeforSetTemporaryCommand> {

	/** The Employee labor defor set temporary command. */
	//@Inject
	//private EmployeeLaborDeforSetTemporaryRepository EmployeeLaborDeforSetTemporaryCommand;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateEmployeeLaborDeforSetTemporaryCommand> context) {
		// TODO Auto-generated method stub

	}

}
