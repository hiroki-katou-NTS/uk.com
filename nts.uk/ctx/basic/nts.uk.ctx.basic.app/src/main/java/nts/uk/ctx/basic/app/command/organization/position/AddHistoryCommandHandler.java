package nts.uk.ctx.basic.app.command.organization.position;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;




@Stateless
public class AddHistoryCommandHandler extends CommandHandler<AddHistoryCommand>{
	
	@Inject
	private PositionRepository positionRepository;
	
	
	protected void handle(CommandHandlerContext<AddHistoryCommand> context) {
		
		String companyCode = AppContexts.user().companyCode();
		String hitoryId = IdentifierUtil.randomUniqueId();
		JobHistory jobHistory = new JobHistory(companyCode,hitoryId,context.getCommand().getEndDate(),context.getCommand().getStartDate());
		positionRepository.addHistory(jobHistory);
	}
	
}