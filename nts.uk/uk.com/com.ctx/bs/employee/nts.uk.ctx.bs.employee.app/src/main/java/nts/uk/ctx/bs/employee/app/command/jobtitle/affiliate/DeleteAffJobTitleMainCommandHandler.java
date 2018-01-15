package nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.AffJobTitleHistoryService;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistoryItemRepository_ver1;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistoryRepository_ver1;
import nts.uk.ctx.bs.employee.dom.jobtitle.affiliate.ver1.AffJobTitleHistory_ver1;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;
@Stateless
public class DeleteAffJobTitleMainCommandHandler extends CommandHandler<DeleteAffJobTitleMainCommand>
	implements PeregDeleteCommandHandler<DeleteAffJobTitleMainCommand>{

	@Inject
	private AffJobTitleHistoryRepository_ver1 affJobTitleHistoryRepository_ver1;
	
	@Inject
	private AffJobTitleHistoryItemRepository_ver1 affJobTitleHistoryItemRepository_v1;
	
	@Inject 
	private AffJobTitleHistoryService affJobTitleHistoryService;
	
	@Override
	public String targetCategoryCd() {
		return "CS00016";
	}

	@Override
	public Class<?> commandClass() {
		return DeleteAffJobTitleMainCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeleteAffJobTitleMainCommand> context) {
		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		Optional<AffJobTitleHistory_ver1> existHist = affJobTitleHistoryRepository_ver1.getListBySid(companyId, command.getEmployeeId());
		
		if (!existHist.isPresent()){
			throw new RuntimeException("invalid AffWorkplaceHistory"); 
		}
		Optional<DateHistoryItem> itemToBeDelete = existHist.get().getHistoryItems().stream()
                .filter(h -> h.identifier().equals(command.getHistId()))
                .findFirst();
		
		if (!itemToBeDelete.isPresent()){
			throw new RuntimeException("invalid AffWorkplaceHistory");
		}
		existHist.get().remove(itemToBeDelete.get());
		
		affJobTitleHistoryService.delete(existHist.get(), itemToBeDelete.get());
		
		affJobTitleHistoryItemRepository_v1.delete(command.getHistId());
	}

}
