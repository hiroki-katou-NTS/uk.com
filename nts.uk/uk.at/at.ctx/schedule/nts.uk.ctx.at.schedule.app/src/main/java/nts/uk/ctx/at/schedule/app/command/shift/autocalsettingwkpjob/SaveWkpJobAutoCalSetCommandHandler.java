/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.autocalsettingwkpjob;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSetting;
import nts.uk.ctx.at.schedule.dom.shift.autocalsetting.WkpJobAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveWkpJobAutoCalSetCommandHandler.
 */
@Stateless
public class SaveWkpJobAutoCalSetCommandHandler extends CommandHandler<WkpJobAutoCalSetCommand> {

	/** The wkp job auto cal setting repo. */
	@Inject
	private WkpJobAutoCalSettingRepository wkpJobAutoCalSettingRepo;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WkpJobAutoCalSetCommand> context) {
		// Get context info
		String companyId = AppContexts.user().companyId();

		// Get command
		WkpJobAutoCalSetCommand command = context.getCommand();

		// Find details
		Optional<WkpJobAutoCalSetting> result = this.wkpJobAutoCalSettingRepo.getAllWkpJobAutoCalSetting(companyId,
				command.getWkp(), command.getJob());

		// Check exist
		if (!result.isPresent()) {
			throw new BusinessException("Msg_3");
		}

		// Convert to domain
		WkpJobAutoCalSetting AutoCalSetting = command.toDomain(companyId);

		// Alway has 30 items and allow update only
		this.wkpJobAutoCalSettingRepo.update(AutoCalSetting);
	}

}
