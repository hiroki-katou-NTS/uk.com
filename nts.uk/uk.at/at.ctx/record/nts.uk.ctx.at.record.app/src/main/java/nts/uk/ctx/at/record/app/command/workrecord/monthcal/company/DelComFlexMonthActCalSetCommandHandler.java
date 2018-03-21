/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.company;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteCompanyCalMonthlyFlexCommandHandler.
 */
@Stateless
public class DelComFlexMonthActCalSetCommandHandler
		extends CommandHandler<DelComFlexMonthActCalSetCommand> {

	/** The com cal monthly flex repo. */
	// @Inject
	// private ComFlexMonthActCalSetRepository comFlexMonthActCalSetRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DelComFlexMonthActCalSetCommand> context) {
		// this.comFlexMonthActCalSetRepo.remove(AppContexts.user().companyId());
	}

}
