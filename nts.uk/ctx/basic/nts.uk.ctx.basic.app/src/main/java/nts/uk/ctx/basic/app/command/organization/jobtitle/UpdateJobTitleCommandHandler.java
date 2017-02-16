package nts.uk.ctx.basic.app.command.organization.jobtitle;


import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.basic.dom.organization.jobtitle.HiterarchyOrderCode;
import nts.uk.ctx.basic.dom.organization.jobtitle.JobCode;
import nts.uk.ctx.basic.dom.organization.jobtitle.JobName;
import nts.uk.ctx.basic.dom.organization.jobtitle.JobTitle;
import nts.uk.ctx.basic.dom.organization.jobtitle.JobTitleRepository;
import nts.uk.ctx.basic.dom.organization.jobtitle.PresenceCheckScopeSet;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

@Stateless
public class UpdateJobTitleCommandHandler extends CommandHandler<UpdateJobTitleCommand> {

	@Inject
	private JobTitleRepository positionRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateJobTitleCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		
		
		if(!JobCode.isExisted(companyCode, 
				new JobCode(context.getCommand().getJobCode()))){
			//throw err[ER026]
		}
		
		JobTitle jobTitle = new JobTitle(
				new JobName(context.getCommand().getJobName()),
				GeneralDate.localDate(context.getCommand().getEndDate()),
				new JobCode(context.getCommand().getJobCode()),
				new JobCode(context.getCommand().getJobOutCode()),
				GeneralDate.localDate(context.getCommand().getStartDate()),
				companyCode,
				new Memo(context.getCommand().getMemo()),
				new HiterarchyOrderCode(context.getCommand().getHiterarchyOrderCode()),
				PresenceCheckScopeSet.valueOf(Integer.toString(context.getCommand().getPresenceCheckScopeSet()))
				);
		positionRepository.update(jobTitle);
	}

}
