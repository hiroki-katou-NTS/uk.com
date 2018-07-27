package nts.uk.ctx.at.request.app.command.vacation.history;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.vacation.history.VacationHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RemoveVacationHistoryCommandHandler.
 */
@Stateless
public class RemoveVacationHistoryCommandHandler extends CommandHandler<RemoveVacationHistoryCommand>{
	
	/** The history repository. */
	@Inject
	private VacationHistoryRepository historyRepository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<RemoveVacationHistoryCommand> context) {
		RemoveVacationHistoryCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		this.historyRepository.removeVacationHistory(companyId, command.getHistoryId(), command.getWorkTypeCode());
	}
}
