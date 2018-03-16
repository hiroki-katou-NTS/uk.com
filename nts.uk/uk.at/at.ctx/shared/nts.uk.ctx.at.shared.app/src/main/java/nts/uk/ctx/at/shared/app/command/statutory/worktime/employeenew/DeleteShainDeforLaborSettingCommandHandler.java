/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.employeenew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;


/**
 * The Class DeleteShainDeforLaborSettingCommandHandler.
 */
@Stateless
public class DeleteShainDeforLaborSettingCommandHandler extends CommandHandler<DeleteShainDeforLaborSettingCommand> {

	/** The emp defor labor setting repo. */
	//@Inject
	//private EmpDeforLaborSettingRepository empDeforLaborSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteShainDeforLaborSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
