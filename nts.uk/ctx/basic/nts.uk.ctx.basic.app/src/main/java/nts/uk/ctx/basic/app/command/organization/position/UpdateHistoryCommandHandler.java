package nts.uk.ctx.basic.app.command.organization.position;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.basic.dom.organization.position.JobHistory;
import nts.uk.ctx.basic.dom.organization.position.PositionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateHistoryCommandHandler extends CommandHandler<UpdateHistoryCommand> {

	@Inject
	private PositionRepository positionRepository;

	@Override
	protected void handle(CommandHandlerContext<UpdateHistoryCommand> context) {

		UpdateHistoryCommand command = context.getCommand();
		
		if (positionRepository.CheckUpdateHistory(command.getHistoryId(), command.getStartDate())) {
			JobHistory jobHistory = command.toDomain();
			jobHistory.validate();
			positionRepository.updateHistory(jobHistory);
		}

	}

}
