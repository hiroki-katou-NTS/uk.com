/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteEmpFlexSettingCommandHandler.
 */
@Stateless
public class DeleteEmpFlexSettingCommandHandler extends CommandHandler<DeleteEmpFlexSettingCommand> {

	/** The emp ment flex setting repo. */
	//@Inject
	//private EmpMentFlexSettingRepository empMentFlexSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteEmpFlexSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
