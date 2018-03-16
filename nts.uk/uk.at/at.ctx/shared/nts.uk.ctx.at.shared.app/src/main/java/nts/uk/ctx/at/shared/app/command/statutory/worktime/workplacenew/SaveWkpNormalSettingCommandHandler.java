/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class SaveWkpNormalSettingCommandHandler.
 */
@Stateless
public class SaveWkpNormalSettingCommandHandler extends CommandHandler<SaveWkpNormalSettingCommand> {

	/** The wkp normal setting repo. */
	//@Inject
	//private WkpNormalSettingRepository wkpNormalSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveWkpNormalSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
