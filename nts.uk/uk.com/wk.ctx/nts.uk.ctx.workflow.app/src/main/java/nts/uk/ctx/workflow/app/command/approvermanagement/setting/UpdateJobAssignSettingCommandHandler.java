package nts.uk.ctx.workflow.app.command.approvermanagement.setting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSettingRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author yennth
 *
 */
@Stateless
@Transactional
public class UpdateJobAssignSettingCommandHandler extends CommandHandler<JobAssignSettingCommand>{
	@Inject
	private JobAssignSettingRepository jobRep;
	/**
	 * update job assign setting
	 */

	@Override
	protected void handle(CommandHandlerContext<JobAssignSettingCommand> context) {
		JobAssignSettingCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<JobAssignSetting> job = jobRep.findById();
		JobAssignSetting jobAssign = JobAssignSetting.createFromJavaType(companyId, command.getIsConcurrently());
		jobAssign.validate();
		if(job.isPresent()){
			jobRep.updateJob(jobAssign);
		}
		jobRep.insertJob(jobAssign);
	}
	
}
