/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class UpdateComNormalSettingCommandHandler.
 */
@Stateless
public class UpdateComNormalSettingCommandHandler extends CommandHandler<UpdateComNormalSettingCommand> {

	/** The com normal setting repo. */
	//@Inject
	//private ComNormalSettingRepository comNormalSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateComNormalSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
