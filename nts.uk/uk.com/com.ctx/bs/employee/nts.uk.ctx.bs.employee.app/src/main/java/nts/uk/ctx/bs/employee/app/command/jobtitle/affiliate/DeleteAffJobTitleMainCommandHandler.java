package nts.uk.ctx.bs.employee.app.command.jobtitle.affiliate;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.jobtile.affiliate.AffJobTitleHistoryItemRepository_v1;
import nts.uk.ctx.bs.employee.dom.jobtile.affiliate.AffJobTitleHistoryRepository_ver1;
import nts.uk.ctx.bs.employee.dom.jobtile.affiliate.AffJobTitleHistory_ver1;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;
@Stateless
public class DeleteAffJobTitleMainCommandHandler extends CommandHandler<DeleteAffJobTitleMainCommand>
	implements PeregDeleteCommandHandler<DeleteAffJobTitleMainCommand>{

	@Inject
	private AffJobTitleHistoryRepository_ver1 affJobTitleHistoryRepository_ver1;
	
	@Inject
	private AffJobTitleHistoryItemRepository_v1 affJobTitleHistoryItemRepository_v1;
	
	@Override
	public String targetCategoryCd() {
		return "CS00009";
	}

	@Override
	public Class<?> commandClass() {
		return DeleteAffJobTitleMainCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeleteAffJobTitleMainCommand> context) {
		val command = context.getCommand();
		
		Optional<AffJobTitleHistory_ver1> existHist = affJobTitleHistoryRepository_ver1.getListBySid(command.getEmployeeId());
		
		if (!existHist.isPresent()){
			throw new RuntimeException("invalid AffWorkplaceHistory"); 
		}
		if (existHist.get().getHistoryItems().size() > 0){
			
			Optional<DateHistoryItem> itemToBeDelete = existHist.get().getHistoryItems().stream()
                    .filter(h -> h.identifier().equals(command.getHistId()))
                    .findFirst();
			
			if (!itemToBeDelete.isPresent()){
				throw new RuntimeException("invalid AffWorkplaceHistory");
			}
			existHist.get().remove(itemToBeDelete.get());
			
			affJobTitleHistoryRepository_ver1.deleteJobTitleMain(existHist.get(), itemToBeDelete.get());
		}
		
		affJobTitleHistoryItemRepository_v1.deleteJobTitleMain(command.getHistId());
	}

}
