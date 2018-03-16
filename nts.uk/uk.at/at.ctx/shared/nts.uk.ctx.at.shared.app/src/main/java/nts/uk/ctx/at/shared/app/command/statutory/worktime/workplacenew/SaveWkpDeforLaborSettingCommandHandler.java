/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class SaveWkpDeforLaborSettingCommandHandler.
 */
@Stateless
public class SaveWkpDeforLaborSettingCommandHandler extends CommandHandler<SaveWkpDeforLaborSettingCommand> {

	/** The wkp defor labor setting repo. */
	//@Inject
	//private WkpDeforLaborSettingRepository wkpDeforLaborSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveWkpDeforLaborSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
