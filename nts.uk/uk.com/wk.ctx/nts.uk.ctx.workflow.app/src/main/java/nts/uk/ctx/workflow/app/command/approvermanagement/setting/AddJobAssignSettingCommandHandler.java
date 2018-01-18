package nts.uk.ctx.workflow.app.command.approvermanagement.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.JobAssignSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * add job assign setting
 * @author yennth
 */
@Stateless
@Transactional
public class AddJobAssignSettingCommandHandler extends CommandHandler<JobAssignSettingCommand>{
	@Inject
	private JobAssignSettingRepository jobRep;
	/**
	 * add job assign setting
	 * @author yennth
	 */
	@Override
	protected void handle(CommandHandlerContext<JobAssignSettingCommand> context) {
		JobAssignSettingCommand data = context.getCommand();
		String companyId = AppContexts.user().companyId();
		JobAssignSetting job = jobRep.findById(companyId);
		if(job == null){
			throw new BusinessException("Msg_3");
		}
		JobAssignSetting jobSet = data.toDomain(companyId);
		jobSet.validate();
		jobRep.insertJob(jobSet);
	}
}
