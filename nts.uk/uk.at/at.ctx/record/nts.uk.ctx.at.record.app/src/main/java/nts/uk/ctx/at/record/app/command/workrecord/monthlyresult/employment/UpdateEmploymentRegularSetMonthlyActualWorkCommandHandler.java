/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employment;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class UpdateEmploymentRegularSetMonthlyActualWorkCommandHandler.
 */
@Stateless
public class UpdateEmploymentRegularSetMonthlyActualWorkCommandHandler
		extends CommandHandler<UpdateEmploymentRegularSetMonthlyActualWorkCommand> {

	/** The empl reg set monthly actual work repo. */
	//@Inject
	//private EmploymentRegularSetMonthlyActualWorkRepository emplRegSetMonthlyActualWorkRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateEmploymentRegularSetMonthlyActualWorkCommand> context) {
		// TODO Auto-generated method stub

	}

}
