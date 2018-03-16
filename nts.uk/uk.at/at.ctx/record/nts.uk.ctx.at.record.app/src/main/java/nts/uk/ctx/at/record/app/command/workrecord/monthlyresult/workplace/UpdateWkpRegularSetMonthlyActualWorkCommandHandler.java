/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.monthlyresult.workplace.WkpRegularSetMonthlyActualWorkRepository;

/**
 * The Class UpdateWkpRegularSetMonthlyActualWorkCommandHandler.
 */
@Stateless
public class UpdateWkpRegularSetMonthlyActualWorkCommandHandler
		extends CommandHandler<UpdateWkpRegularSetMonthlyActualWorkCommand> {

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
	protected void handle(CommandHandlerContext<UpdateWkpRegularSetMonthlyActualWorkCommand> context) {
		// TODO Auto-generated method stub

	}

}
