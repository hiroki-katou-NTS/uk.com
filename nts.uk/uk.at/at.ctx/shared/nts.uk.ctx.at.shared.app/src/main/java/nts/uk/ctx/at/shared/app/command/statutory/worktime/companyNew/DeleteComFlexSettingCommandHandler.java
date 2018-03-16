/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteComFlexSettingCommandHandler.
 */
@Stateless
public class DeleteComFlexSettingCommandHandler extends CommandHandler<DeleteComFlexSettingCommand> {

	/** The com flex setting repo. */
	//@Inject
	//private ComFlexSettingRepository comFlexSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteComFlexSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
