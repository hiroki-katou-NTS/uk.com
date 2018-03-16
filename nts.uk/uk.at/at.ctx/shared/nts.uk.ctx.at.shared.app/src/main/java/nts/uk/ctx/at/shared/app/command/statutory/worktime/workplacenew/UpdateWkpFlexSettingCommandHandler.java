/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.workplacenew;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSettingRepository;

/**
 * The Class UpdateWkpFlexSettingCommandHandler.
 */
@Stateless
public class UpdateWkpFlexSettingCommandHandler extends CommandHandler<UpdateWkpFlexSettingCommand> {

	/** The wkp flex setting repo. */
	//@Inject
	//private WkpFlexSettingRepository wkpFlexSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateWkpFlexSettingCommand> context) {
		// TODO Auto-generated method stub

	}

}
