/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.employment;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class SaveEmploymentCalMonthlyFlexCommandHandler.
 */
@Stateless
public class SaveEmploymentCalMonthlyFlexCommandHandler extends CommandHandler<SaveEmploymentCalMonthlyFlexCommand> {

	/** The emp cal monthly flex repo. */
	//@Inject
	//private EmploymentCalMonthlyFlexRepository empCalMonthlyFlexRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveEmploymentCalMonthlyFlexCommand> context) {
		// TODO Auto-generated method stub

	}

}
