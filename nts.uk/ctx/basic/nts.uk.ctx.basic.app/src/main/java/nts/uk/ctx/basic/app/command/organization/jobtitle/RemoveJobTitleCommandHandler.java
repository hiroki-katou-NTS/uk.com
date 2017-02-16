package nts.uk.ctx.basic.app.command.organization.jobtitle;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.organization.jobtitle.JobCode;
import nts.uk.ctx.basic.dom.organization.jobtitle.JobTitleRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RemoveJobTitleCommandHandler extends CommandHandler<RemoveJobTitleCommand> {

	@Inject
	private JobTitleRepository positionRepository;

	@Override
	protected void handle(CommandHandlerContext<RemoveJobTitleCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		if(!positionRepository.isExisted(companyCode, 
				new JobCode(context.getCommand().getJobCode()))){
			//throw err[ER010]
		}
		positionRepository.remove(companyCode,
				new JobCode(context.getCommand().getJobCode()));

	}

}
