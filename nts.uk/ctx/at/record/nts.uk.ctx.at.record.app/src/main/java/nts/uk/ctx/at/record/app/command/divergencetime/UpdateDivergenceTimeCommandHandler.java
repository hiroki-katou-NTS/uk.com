package nts.uk.ctx.at.record.app.command.divergencetime;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTimeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateDivergenceTimeCommandHandler extends CommandHandler<UpdateDivergenceTimeCommand>{

	@Inject
	private DivergenceTimeRepository divTimeRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateDivergenceTimeCommand> context) {
		String companyId = AppContexts.user().companyCode();
		DivergenceTime divTime = DivergenceTime.createSimpleFromJavaType(companyId,
									context.getCommand().getDivTimeId(),
									context.getCommand().getDivTimeUseSet(),
									context.getCommand().getAlarmTime(),
									context.getCommand().getErrTime(),
									context.getCommand().getSelectSet().getSelectUseSet(), 
									context.getCommand().getSelectSet().getCancelErrSelReason(),
									context.getCommand().getInputSet().getSelectUseSet(),
									context.getCommand().getInputSet().getCancelErrSelReason());
		divTimeRepo.updateDivTime(divTime);
	}
	
}
