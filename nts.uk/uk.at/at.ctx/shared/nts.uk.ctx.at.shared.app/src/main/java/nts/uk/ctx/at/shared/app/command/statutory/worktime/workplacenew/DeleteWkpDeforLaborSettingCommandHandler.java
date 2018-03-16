/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class DeleteWkpDeforLaborSettingCommandHandler.
 */
@Stateless
public class DeleteWkpDeforLaborSettingCommandHandler extends CommandHandler<DeleteWkpDeforLaborSettingCommand> {

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
	protected void handle(CommandHandlerContext<DeleteWkpDeforLaborSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
