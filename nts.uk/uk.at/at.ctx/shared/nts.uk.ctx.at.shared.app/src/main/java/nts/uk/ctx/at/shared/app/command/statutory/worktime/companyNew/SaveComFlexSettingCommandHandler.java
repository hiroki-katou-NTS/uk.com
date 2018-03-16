/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting;

/**
 * The Class SaveComFlexSettingCommandHandler.
 */
@Stateless
public class SaveComFlexSettingCommandHandler extends CommandHandler<SaveComFlexSettingCommand> {

	/** The com flex setting repo. */
	//@Inject
	//private ComFlexSettingRepository comFlexSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveComFlexSettingCommand> context) {
		
		// convert to domain
		SaveComFlexSettingCommand saveComFlexSettingCommand = context.getCommand();

		ComFlexSetting comFlexSetting = new ComFlexSetting(saveComFlexSettingCommand);

		//this.comFlexSettingRepo.add(comFlexSetting);

	}

}
