/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employee;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteEmployeeRegularSetMonthlyActualCommandHandler.
 */
@Stateless
public class DeleteEmployeeRegularSetMonthlyActualCommandHandler
		extends CommandHandler<DeleteEmployeeRegularSetMonthlyActualCommand> {

	/** The emp reg set monthly actual repo. */
	//@Inject
	//private EmployeeRegularSetMonthlyActualRepository empRegSetMonthlyActualRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteEmployeeRegularSetMonthlyActualCommand> context) {
		// TODO Auto-generated method stub

	}

}
