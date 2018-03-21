/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.workplace;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteCompanyCalMonthlyFlexCommandHandler.
 */
@Stateless
public class DelWkpMonthCalSetCommandHandler
		extends CommandHandler<DelWkpMonthCalSetCommand> {

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
	protected void handle(CommandHandlerContext<DelWkpMonthCalSetCommand> context) {
		// this.comFlexMonthActCalSetRepo.remove(AppContexts.user().companyId());
	}

}
