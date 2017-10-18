/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.ot.autocalsetting.job;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.JobAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.job.JobAutoCalSettingRepository;
import nts.uk.shr.com.context.AppContexts;


/**
 * The Class SaveJobAutoCalSetCommandHandler.
 */
@Stateless
public class SaveJobAutoCalSetCommandHandler extends CommandHandler<JobAutoCalSetCommand> {


    /** The job auto cal setting repo. */
    @Inject
    private JobAutoCalSettingRepository jobAutoCalSettingRepo;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<JobAutoCalSetCommand> context) {
		// Get context info
        String companyId = AppContexts.user().companyId();

        // Get command
        JobAutoCalSetCommand command = context.getCommand();
        
        // Find details
        Optional<JobAutoCalSetting> result = this.jobAutoCalSettingRepo.getAllJobAutoCalSetting(companyId, command.getJobId());

        // Convert to domain
        JobAutoCalSetting jobAutoCalSetting = command.toDomain(companyId);

        if (!result.isPresent()) {
			this.jobAutoCalSettingRepo.add(jobAutoCalSetting);
		} else {
			this.jobAutoCalSettingRepo.update(jobAutoCalSetting);
		}
	}

}
