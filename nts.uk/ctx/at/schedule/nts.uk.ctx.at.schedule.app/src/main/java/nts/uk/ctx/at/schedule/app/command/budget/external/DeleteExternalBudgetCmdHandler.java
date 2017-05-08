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
public class DeleteExternalBudgetCmdHandler extends CommandHandler<DeleteExternalBudgetCmd> {

	@Inject
	private ExternalBudgetRepository budgetRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteExternalBudgetCmd> context) {
		String companyId = AppContexts.user().companyCode();
		// get command
		DeleteExternalBudgetCmd command = context.getCommand();
		Optional<ExternalBudget> optional = this.budgetRepo.find(companyId, command.getExternalBudgetCode());
//		if(!optional.isPresent()){
//			throw new BusinessException(new RawErrorMessage("入力したコードは、既に登録されています。"));
//		}
		// delete process
		budgetRepo.delete(companyId, command.getExternalBudgetCode());
	}

}
