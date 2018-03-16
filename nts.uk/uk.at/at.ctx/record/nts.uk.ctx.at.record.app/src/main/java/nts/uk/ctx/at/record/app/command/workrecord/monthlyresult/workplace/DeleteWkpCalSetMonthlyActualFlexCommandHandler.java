/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.workplace;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteWkpCalSetMonthlyActualFlexCommandHandler.
 */
@Stateless
public class DeleteWkpCalSetMonthlyActualFlexCommandHandler
		extends CommandHandler<DeleteWkpCalSetMonthlyActualFlexCommand> {

	/** The wkp cal set monthly actual flex repo. */
	//@Inject
	//private WkpCalSetMonthlyActualFlexRepository wkpCalSetMonthlyActualFlexRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteWkpCalSetMonthlyActualFlexCommand> context) {
		// TODO Auto-generated method stub

	}

}
