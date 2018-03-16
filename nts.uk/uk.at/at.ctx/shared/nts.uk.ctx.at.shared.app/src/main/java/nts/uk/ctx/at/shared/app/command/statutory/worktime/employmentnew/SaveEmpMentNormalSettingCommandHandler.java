/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class SaveEmpNormalSettingCommandHandler.
 */
@Stateless
public class SaveEmpMentNormalSettingCommandHandler extends CommandHandler<SaveEmpNormalSettingCommand> {

	/** The emp ment normal setting repo. */
	//@Inject
	//private EmpMentNormalSettingRepository empMentNormalSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveEmpNormalSettingCommand> context) {
		// TODO Auto-generated method stub

	}
}
