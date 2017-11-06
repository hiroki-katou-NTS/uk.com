/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveWkpAutoCalSetCommandHandler.
 */
@Stateless
public class SaveWkpAutoCalSetCommandHandler extends CommandHandler<WkpAutoCalSetCommand> {

	/** The wkp auto cal setting repo. */
	@Inject
	private WkpAutoCalSettingRepository wkpAutoCalSettingRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WkpAutoCalSetCommand> context) {
		// Get context info
		String companyId = AppContexts.user().companyId();

		// Get command
		WkpAutoCalSetCommand command = context.getCommand();

		// Find details
		Optional<WkpAutoCalSetting> result = this.wkpAutoCalSettingRepo.getWkpAutoCalSetting(companyId,
				command.getWkpId());

		// Convert to domain
		WkpAutoCalSetting wkpAutoCalSetting = command.toDomain(companyId);

		// check add or update
		if (!result.isPresent()) {
			this.wkpAutoCalSettingRepo.add(wkpAutoCalSetting);
		} else {
			this.wkpAutoCalSettingRepo.update(wkpAutoCalSetting);
		}

	}

}
