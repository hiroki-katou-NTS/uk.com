/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class SaveEmpMentDeforLaborSettingCommandHandler.
 */
@Stateless
public class SaveEmpDeforLaborSettingCommandHandler extends CommandHandler<SaveEmpDeforLaborSettingCommand> {

	/** The emp ment defor labor setting repo. */
	//@Inject
	//private EmpMentDeforLaborSettingRepository empMentDeforLaborSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveEmpDeforLaborSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
