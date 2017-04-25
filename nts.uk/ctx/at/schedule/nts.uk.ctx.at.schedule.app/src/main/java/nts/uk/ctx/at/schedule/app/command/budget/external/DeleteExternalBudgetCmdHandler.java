package nts.uk.ctx.at.schedule.app.command.budget.external;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.shr.com.context.AppContexts;

public class DeleteExternalBudgetCmdHandler extends CommandHandler<DeleteExternalBudgetCmd> {

	@Inject
	private ExternalBudgetRepository budgetRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteExternalBudgetCmd> context) {
		try {
			String companyId = AppContexts.user().companyCode();
			// get command
			DeleteExternalBudgetCmd command = context.getCommand();
			// delete process
			budgetRepo.delete(companyId, command.getExternalBudgetId());
		} catch (Exception ex) {
			throw ex;
		}
	}

}
