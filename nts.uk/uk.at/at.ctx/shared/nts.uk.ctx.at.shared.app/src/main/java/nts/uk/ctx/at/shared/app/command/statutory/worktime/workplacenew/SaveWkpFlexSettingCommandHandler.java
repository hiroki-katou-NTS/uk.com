/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class SaveWkpFlexSettingCommandHandler.
 */
@Stateless
public class SaveWkpFlexSettingCommandHandler extends CommandHandler<SaveWkpFlexSettingCommand> {

	/** The wkp flex setting repo. */
	//@Inject
	//private WkpFlexSettingRepository wkpFlexSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveWkpFlexSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
