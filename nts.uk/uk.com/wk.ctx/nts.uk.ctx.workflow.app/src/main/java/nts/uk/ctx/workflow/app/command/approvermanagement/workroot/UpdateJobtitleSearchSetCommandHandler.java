package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

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
public class UpdateJobtitleSearchSetCommandHandler extends CommandHandler<JobtitleSearchSetCommand>{
	@Inject
	private JobtitleSearchSetRepository jobRep;

	@Override
	protected void handle(CommandHandlerContext<JobtitleSearchSetCommand> context) {
		JobtitleSearchSetCommand data = context.getCommand();
		String companyId = AppContexts.user().companyId();
		JobtitleSearchSet jobtitle = JobtitleSearchSet.createSimpleFromJavaType(companyId, data.getJobId(), data.getSearchSetFlg());
		jobtitle.validate();
		jobRep.update(jobtitle);
	}
	
}
