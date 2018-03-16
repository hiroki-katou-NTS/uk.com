/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.workplace;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class SaveWkpTransLaborSetMonthlyCommandHandler.
 */
@Stateless
public class SaveWkpTransLaborSetMonthlyCommandHandler extends CommandHandler<SaveWkpTransLaborSetMonthlyCommand> {

	/** The wkp trans labor set monthly repo. */
	//@Inject
	//private WkpTransLaborSetMonthlyRepository wkpTransLaborSetMonthlyRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveWkpTransLaborSetMonthlyCommand> context) {
		// TODO Auto-generated method stub
	}

}
