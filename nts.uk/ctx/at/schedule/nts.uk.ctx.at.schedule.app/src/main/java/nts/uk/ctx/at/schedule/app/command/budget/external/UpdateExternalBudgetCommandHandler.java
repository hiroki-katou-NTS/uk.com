package nts.uk.ctx.at.schedule.app.command.budget.external;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudget;
import nts.uk.ctx.at.schedule.dom.budget.external.ExternalBudgetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateExternalBudgetCommandHandler extends CommandHandler<UpdateExternalBudgetCommand> {
	@Inject
	private ExternalBudgetRepository budgetRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateExternalBudgetCommand> context) {
		// get command
		UpdateExternalBudgetCommand command = context.getCommand();
		// convert to server
		ExternalBudget exBudget = command.toDomain();
		Optional<ExternalBudget> optional = this.budgetRepo.find(AppContexts.user().companyCode(), command.getExternalBudgetCode());
		if(!optional.isPresent()){
			throw new BusinessException(new RawErrorMessage("項目が存在しません、削除されしたようです。"));
		}
		// update process
		budgetRepo.update(exBudget);
	}

}
