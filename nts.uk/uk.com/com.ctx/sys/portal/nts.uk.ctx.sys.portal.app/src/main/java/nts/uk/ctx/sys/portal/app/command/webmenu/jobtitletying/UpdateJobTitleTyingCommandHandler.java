package nts.uk.ctx.sys.portal.app.command.webmenu.jobtitletying;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.uk.shr.com.context.AppContexts;
/**
 * @author yennth
 * The class UpdateJobTitleTyingCommandHandler
 */
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying.JobTitleTying;
import nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying.JobTitleTyingRepository;

@Stateless
@Transactional
public class UpdateJobTitleTyingCommandHandler extends CommandHandler<UpdateJobTitleTyingCommand>{
	@Inject
	private JobTitleTyingRepository	jobTitleTyingRepository;
	/**
	 * update job title tying
	 */
	@Override
	protected void handle (CommandHandlerContext<UpdateJobTitleTyingCommand> context){
		List<JobTitleTying> lstJobTitleTying = new ArrayList<>();
		UpdateJobTitleTyingCommand update = context.getCommand();
		String companyId = AppContexts.user().companyId();
		List<JobTitleTyingCommand> jobTitleTyings = update.getJobTitleTyings();
		for(JobTitleTyingCommand obj: jobTitleTyings){
			JobTitleTying newobject = JobTitleTying.updateWebMenuCode(companyId, obj.getJobId(), obj.getWebMenuCode());
			newobject.validate();
			lstJobTitleTying.add(newobject);
		}
		jobTitleTyingRepository.updateAndInsertMenuCode(lstJobTitleTying);
	}
}
