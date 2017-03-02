package nts.uk.ctx.basic.app.command.organization.position;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
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
		String historyID = IdentifierUtil.randomUniqueId();
		if(!positionRepository.isExisted(companyCode, 
				new JobCode(context.getCommand().getJobCode()),historyID)){
			//throw err[ER010]
		}
		positionRepository.delete(companyCode,
				new JobCode(context.getCommand().getJobCode()),historyID);

	}

}
