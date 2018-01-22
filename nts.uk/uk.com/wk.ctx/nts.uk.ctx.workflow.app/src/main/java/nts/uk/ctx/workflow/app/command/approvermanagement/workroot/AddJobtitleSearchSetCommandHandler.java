package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

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
public class AddJobtitleSearchSetCommandHandler extends CommandHandler<JobtitleSearchSetCommand>{
	@Inject
	private JobtitleSearchSetRepository jobRep;

	@Override
	protected void handle(CommandHandlerContext<JobtitleSearchSetCommand> context) {
		JobtitleSearchSetCommand data = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<JobtitleSearchSet> jobtitle = jobRep.finById(companyId, data.getJobId());
		JobtitleSearchSet jobSearch = data.toDomain(data.getJobId());
		jobSearch.validate();
		if(jobtitle.isPresent()){
			jobRep.update(jobSearch);
		}
		jobRep.insert(jobSearch);
	}
	
}
