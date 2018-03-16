/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employment;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class UpdateEmploymentCalMonthlyFlexCommandHandler.
 */
@Stateless
public class UpdateEmploymentCalMonthlyFlexCommandHandler
		extends CommandHandler<UpdateEmploymentCalMonthlyFlexCommand> {

	/** The empl cal monthly flex repo. */
	//@Inject
	//private EmploymentCalMonthlyFlexRepository emplCalMonthlyFlexRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateEmploymentCalMonthlyFlexCommand> context) {
		// TODO Auto-generated method stub

	}

}
