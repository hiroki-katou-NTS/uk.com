/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;


/**
 * The Class UpdateShainFlexSettingCommandHandler.
 */
@Stateless
public class UpdateShainFlexSettingCommandHandler extends CommandHandler<UpdateShainFlexSettingCommand> {

	/** The emp flex setting repo. */
	//@Inject
	//private EmpFlexSettingRepository empFlexSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateShainFlexSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
