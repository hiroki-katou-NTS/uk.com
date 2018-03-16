/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * The Class UpdateComDeformationLaborSettingCommandHandler.
 */
@Stateless
public class UpdateComDeforLaborSettingCommandHandler
		extends CommandHandler<UpdateComDeforLaborSettingCommand> {

	/** The com deformation labor setting repo. */
	//@Inject
	//private ComDeformationLaborSettingRepository comDeformationLaborSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateComDeforLaborSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
