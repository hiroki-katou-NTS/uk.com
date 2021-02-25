/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.wkp;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkp.WkpAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.wkp.WkpAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RemoveJobAutoCalSetCommandHandler.
 */
@Stateless
@Transactional
public class RemoveWkpAutoCalSetCommandHandler extends CommandHandler<RemoveWkpAutoCalSetCommand> {

	/** The job auto cal setting repository. */
	@Inject
	private WkpAutoCalSettingRepository wkpAutoCalSettingRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<RemoveWkpAutoCalSetCommand> context) {
		String companyId = AppContexts.user().companyId();
		RemoveWkpAutoCalSetCommand command = context.getCommand();
		
		// Check exist
		Optional<WkpAutoCalSetting> result = this.wkpAutoCalSettingRepository.getWkpAutoCalSetting(companyId, command.getWkpId());
		if (result.isPresent()) {
			this.wkpAutoCalSettingRepository.delete(companyId, command.getWkpId());
		}		
	}
}
