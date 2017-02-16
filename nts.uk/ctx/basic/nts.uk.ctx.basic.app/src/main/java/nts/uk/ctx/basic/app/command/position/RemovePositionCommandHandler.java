package nts.uk.ctx.basic.app.command.position;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.organization.position.JobCode;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RemovePositionCommandHandler extends CommandHandler<RemovePositionCommand> {

	@Inject
	private PositionRepository positionRepository;

	@Override
	protected void handle(CommandHandlerContext<RemovePositionCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		if(!positionRepository.isExisted(companyCode, 
				new JobCode(context.getCommand().getJobCode()))){
			//throw err[ER010]
		}
		positionRepository.remove(companyCode,
				new JobCode(context.getCommand().getJobCode()));

	}

}
