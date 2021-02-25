/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.job;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.job.JobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.job.JobAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RemoveJobAutoCalSetCommandHandler.
 */
@Stateless
@Transactional
public class RemoveJobAutoCalSetCommandHandler extends CommandHandler<RemoveJobAutoCalSetCommand> {

	/** The job auto cal setting repository. */
	@Inject
	private JobAutoCalSettingRepository jobAutoCalSettingRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<RemoveJobAutoCalSetCommand> context) {
		String companyId = AppContexts.user().companyId();
		RemoveJobAutoCalSetCommand command = context.getCommand();
		
		// Check exist
		Optional<JobAutoCalSetting> result = this.jobAutoCalSettingRepository.getJobAutoCalSetting(companyId, command.getJobId());
		if (result.isPresent()) {
			this.jobAutoCalSettingRepository.delete(companyId, command.getJobId());
		}		
	}
}
