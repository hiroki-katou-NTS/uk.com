package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSet;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.JobtitleSearchSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author yennth
 *
 */
@Stateless
@Transactional
public class UpdateJobtitleSearchSetCommandHandler extends CommandHandler<List<JobtitleSearchSetCommand>>{
	@Inject
	private JobtitleSearchSetRepository jobRep;

	@Override
	protected void handle(CommandHandlerContext<List<JobtitleSearchSetCommand>> context) {
		List<JobtitleSearchSetCommand> data = context.getCommand();
		for(JobtitleSearchSetCommand item : data){
			String companyId = AppContexts.user().companyId();
			Optional<JobtitleSearchSet> jobtitle = jobRep.finById(companyId, item.getJobId());
			JobtitleSearchSet jobSearch = item.toDomain(item.getJobId());
			jobSearch.validate();
			if(jobtitle.isPresent()){
				jobRep.update(jobSearch);
			}else{
				jobRep.insert(jobSearch);
			}
		}
	}
}
