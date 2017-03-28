package nts.uk.ctx.basic.app.command.organization.position;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.organization.position.JobTitleRef;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;

import nts.uk.shr.com.context.AppContexts;
@Stateless
public class AddJobTitleRefCommandHandler extends CommandHandler<AddJobTitleRefCommand>{

	@Inject
	private PositionRepository positionRepository;
	@Override
	protected void handle(CommandHandlerContext<AddJobTitleRefCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		JobTitleRef jTitleRef = new JobTitleRef(context.getCommand().getAuthorizationCode(), 
								companyCode, 
								context.getCommand().getHistoryId(), 
								context.getCommand().getJobCode(), 	
								context.getCommand().getReferenceSettings());
		positionRepository.addJobTitleRef(jTitleRef);
	}


}
