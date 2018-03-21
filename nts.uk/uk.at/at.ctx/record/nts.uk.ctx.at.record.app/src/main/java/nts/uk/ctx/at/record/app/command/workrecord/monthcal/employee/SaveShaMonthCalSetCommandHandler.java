/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.monthcal.employee;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveCompanyCalMonthlyFlexCommandHandler.
 */
@Stateless
public class SaveShaMonthCalSetCommandHandler
		extends CommandHandler<SaveShaMonthCalSetCommand> {

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
	protected void handle(CommandHandlerContext<SaveShaMonthCalSetCommand> context) {
		String cid = AppContexts.user().companyId();
	}

}
