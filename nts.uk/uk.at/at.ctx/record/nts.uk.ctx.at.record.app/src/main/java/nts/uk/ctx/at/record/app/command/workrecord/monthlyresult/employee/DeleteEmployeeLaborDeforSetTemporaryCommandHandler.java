/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employee;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteEmployeeLaborDeforSetTemporaryCommandHandler.
 */
@Stateless
public class DeleteEmployeeLaborDeforSetTemporaryCommandHandler
		extends CommandHandler<DeleteEmployeeLaborDeforSetTemporaryCommand> {

	/** The emp labor defor set temp command. */
	//@Inject
	//private EmployeeLaborDeforSetTemporaryRepository empLaborDeforSetTempCommand;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteEmployeeLaborDeforSetTemporaryCommand> context) {
		// TODO Auto-generated method stub

	}

}
