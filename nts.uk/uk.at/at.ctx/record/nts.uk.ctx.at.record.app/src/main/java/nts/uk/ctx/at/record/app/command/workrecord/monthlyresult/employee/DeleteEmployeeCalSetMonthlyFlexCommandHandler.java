/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employee;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteEmployeeCalSetMonthlyFlexCommandHandler.
 */
@Stateless
public class DeleteEmployeeCalSetMonthlyFlexCommandHandler
		extends CommandHandler<DeleteEmployeeCalSetMonthlyFlexCommand> {

	/** The emp cal set monthly flex repo. */
	//@Inject
	//private EmployeeCalSetMonthlyFlexRepository empCalSetMonthlyFlexRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteEmployeeCalSetMonthlyFlexCommand> context) {
		// TODO Auto-generated method stub

	}

}
