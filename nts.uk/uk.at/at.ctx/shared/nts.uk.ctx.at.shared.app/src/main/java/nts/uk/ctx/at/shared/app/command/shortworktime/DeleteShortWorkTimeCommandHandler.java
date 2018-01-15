package nts.uk.ctx.at.shared.app.command.shortworktime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistoryRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.command.PeregDeleteCommandHandler;

@Stateless
public class DeleteShortWorkTimeCommandHandler extends CommandHandler<DeleteShortWorkTimeCommand>
	implements PeregDeleteCommandHandler<DeleteShortWorkTimeCommand>{
	
	@Inject
	private SWorkTimeHistoryRepository sWorkTimeHistoryRepository;
	
	@Inject 
	private SWorkTimeHistItemRepository sWorkTimeHistItemRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00019";
	}

	@Override
	public Class<?> commandClass() {
		return DeleteShortWorkTimeCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<DeleteShortWorkTimeCommand> context) {
		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		Optional<ShortWorkTimeHistory> existHist = sWorkTimeHistoryRepository.getBySid(companyId, command.getEmployeeId());
		// In case of exist history of this employee
		if (!existHist.isPresent()){
			throw new RuntimeException("Invalid ShortWorkTimeHistory");
		}
		
		Optional<DateHistoryItem> itemtoBeDeleted = existHist.get().getHistoryItems().stream().filter(hst->hst.identifier().equals(command.getHistoryId())).findFirst();
		if (!itemtoBeDeleted.isPresent()){
			throw new RuntimeException("Invalid ShortWorkTimeHistory");
		}
		existHist.get().remove(itemtoBeDeleted.get());
		sWorkTimeHistoryRepository.delete(command.getEmployeeId(),command.getHistoryId());
		sWorkTimeHistItemRepository.delete(command.getEmployeeId(),command.getHistoryId());
	}

}
