/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthlyresult.company;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteCompanyCalMonthlyFlexCommandHandler.
 */
@Stateless
public class DeleteCompanyCalMonthlyFlexCommandHandler extends CommandHandler<DeleteCompanyCalMonthlyFlexCommand> {

	/** The com cal monthly flex repo. */
	//@Inject
	//private CompanyCalMonthlyFlexRepository comCalMonthlyFlexRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteCompanyCalMonthlyFlexCommand> context) {
		// TODO Auto-generated method stub

	}

}
