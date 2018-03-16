/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;


/**
 * The Class SaveShainNormalSettingCommandHandler.
 */
@Stateless
public class SaveShainNormalSettingCommandHandler extends CommandHandler<SaveShainNormalSettingCommand> {

	/** The emp normal setting repo. */
	//@Inject
	//private EmpNormalSettingRepository empNormalSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveShainNormalSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
