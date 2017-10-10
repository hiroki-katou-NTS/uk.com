/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkpjob;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkpjob.WkpJobAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveWkpJobAutoCalSetCommandHandler.
 */
@Stateless
public class SaveWkpJobAutoCalSetCommandHandler extends CommandHandler<WkpJobAutoCalSetCommand> {

	/** The wkp job auto cal setting repo. */
	@Inject
	private WkpJobAutoCalSettingRepository wkpJobAutoCalSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WkpJobAutoCalSetCommand> context) {
		// Get context info
		String companyId = AppContexts.user().companyId();

		// Get command
		WkpJobAutoCalSetCommand command = context.getCommand();

		// Find details
		Optional<WkpJobAutoCalSetting> result = this.wkpJobAutoCalSettingRepo.getAllWkpJobAutoCalSetting(companyId,
				command.getWkpId(), command.getJobId());

		// Convert to domain
		WkpJobAutoCalSetting AutoCalSetting = command.toDomain(companyId);

		// check add or update
		if (!result.isPresent()) {
			this.wkpJobAutoCalSettingRepo.add(AutoCalSetting);
		} else {
			this.wkpJobAutoCalSettingRepo.update(AutoCalSetting);
		}
	}

}
