/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.workplace;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteWkpRegularSetMonthlyActualWorkCommandHandler.
 */
@Stateless
public class DeleteWkpRegularSetMonthlyActualWorkCommandHandler
		extends CommandHandler<DeleteWkpRegularSetMonthlyActualWorkCommand> {

	/** The wkp reg set monthly actual work repo. */
	//@Inject
	//private WkpRegularSetMonthlyActualWorkRepository wkpRegSetMonthlyActualWorkRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteWkpRegularSetMonthlyActualWorkCommand> context) {
		// TODO Auto-generated method stub

	}

}
