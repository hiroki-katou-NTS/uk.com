package nts.uk.ctx.sys.portal.app.command.webmenu.jobtitletying;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
		String companyId = AppContexts.user().companyId();
		List<JobTitleTying> lstJobTitleTyingAdd = new ArrayList<>();
		List<JobTitleTying> lstJobTitleTyingUpdate = new ArrayList<>();
		List<JobTitleTyingCommand> jobTitleTyings = context.getCommand().getJobTitleTyings();
		List<JobTitleTying> listJobTitleTyingUpdate = jobTitleTyings.stream().map(c -> {
			return JobTitleTying.createFromJavaType(companyId, c.getJobId(), c.getWebMenuCode());
		}).collect(Collectors.toList());
		if (listJobTitleTyingUpdate.size() == 0) {
			return;
		}
		for (JobTitleTying jobTitleTying : listJobTitleTyingUpdate) {
			if(jobTitleTying.getJobId().equals(null)){
				lstJobTitleTyingAdd.add(jobTitleTying);
			}else{
				lstJobTitleTyingUpdate.add(jobTitleTying);
			}
		}
		if(lstJobTitleTyingUpdate.size()>0){
			jobTitleTyingRepository.updateMenuCode(lstJobTitleTyingUpdate);
		}
		if(lstJobTitleTyingAdd.size()>0){
			jobTitleTyingRepository.insertMenuCode(lstJobTitleTyingAdd);;
		}
	}
}
