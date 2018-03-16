/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting;

/**
 * The Class SaveComDeformationLaborSettingCommandHandler.
 */
@Stateless
public class SaveComDeforLaborSettingCommandHandler
		extends CommandHandler<SaveComDeforLaborSettingCommand> {

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
	protected void handle(CommandHandlerContext<SaveComDeforLaborSettingCommand> context) {

		// convert to domain
		SaveComDeforLaborSettingCommand saveComDeforLaborSettingCommand = context.getCommand();

		ComDeforLaborSetting comDeforLaborSet = new ComDeforLaborSetting(saveComDeforLaborSettingCommand);

		//this.comDeformationLaborSettingRepo.add(comDeforLaborSet);

	}

}
