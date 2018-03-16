/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employmentnew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class UpdateEmpMentDeforLaborSettingCommandHandler.
 */
@Stateless
public class UpdateEmpDeforLaborSettingCommandHandler
		extends CommandHandler<UpdateEmpDeforLaborSettingCommand> {

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
	protected void handle(CommandHandlerContext<UpdateEmpDeforLaborSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
