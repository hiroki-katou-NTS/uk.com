/**
 * 
 */
package nts.uk.ctx.bs.employee.app.command.classification.affiliate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistItemRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistoryRepositoryService;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistoryRepository_ver1;
import nts.uk.ctx.bs.employee.dom.classification.affiliate_ver1.AffClassHistory_ver1;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

/**
 * @author danpv
 * @author hop.nt
 */
@Stateless
public class DeleteAffClassCommandHandler extends CommandHandler<DeleteAffClassificationCommand>
		implements PeregDeleteCommandHandler<DeleteAffClassificationCommand> {
	
	@Inject
	private AffClassHistoryRepository_ver1 affClassHistoryRepo;

	@Inject
	private AffClassHistItemRepository_ver1 affClassHistItemRepo;
	
	@Inject
	private AffClassHistoryRepositoryService affClassHistoryRepositoryService;

	@Override
	public String targetCategoryCd() {
		return "CS00004";
	}

	@Override
	public Class<?> commandClass() {
		return DeleteAffClassificationCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeleteAffClassificationCommand> context) {
		DeleteAffClassificationCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		Optional<AffClassHistory_ver1> historyOption = affClassHistoryRepo.getByEmployeeId(companyId,command.getEmployeeId());
		if ( !historyOption.isPresent()) {
			throw new RuntimeException("Invalid AffClassHistory_ver1");
		}
		AffClassHistory_ver1 history = historyOption.get();
		Optional<DateHistoryItem> itemToBeDeleteOpt = history.getPeriods().stream()
				.filter(h -> h.identifier().equals(command.getHistoryId())).findFirst();
		if ( !itemToBeDeleteOpt.isPresent()) {
			throw new RuntimeException("Invalid AffClassHistory_ver1");
		}
		DateHistoryItem itemToBeDelete = itemToBeDeleteOpt.get();
		historyOption.get().remove(itemToBeDelete);
		
		affClassHistoryRepositoryService.delete(history, itemToBeDelete);
		
		affClassHistItemRepo.delete(command.getHistoryId());
	}

}
