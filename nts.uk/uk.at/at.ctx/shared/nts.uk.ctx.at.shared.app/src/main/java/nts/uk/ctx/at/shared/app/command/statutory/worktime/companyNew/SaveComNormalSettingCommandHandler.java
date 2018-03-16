/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;

/**
 * The Class SaveComNormalSettingCommandHandler.
 */
@Stateless
public class SaveComNormalSettingCommandHandler extends CommandHandler<SaveComNormalSettingCommand> {

	/** The com normal setting repo. */
	//@Inject
	//private ComNormalSettingRepository comNormalSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SaveComNormalSettingCommand> context) {

		// convert to domain
		SaveComNormalSettingCommand saveComNormalSettingCommand = context.getCommand();

		ComNormalSetting comNormalSetting = new ComNormalSetting(saveComNormalSettingCommand);

		//this.comNormalSettingRepo.add(comNormalSetting);

	}

}
