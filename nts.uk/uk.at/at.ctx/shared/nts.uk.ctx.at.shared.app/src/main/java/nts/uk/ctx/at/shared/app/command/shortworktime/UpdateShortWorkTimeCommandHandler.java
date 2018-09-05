package nts.uk.ctx.at.shared.app.command.shortworktime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistoryRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateShortWorkTimeCommandHandler extends CommandHandler<UpdateShortWorkTimeCommand>
	implements PeregUpdateCommandHandler<UpdateShortWorkTimeCommand>{
	
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
		return UpdateShortWorkTimeCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateShortWorkTimeCommand> context) {
		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		if (command.getStartDate() != null){
			DateHistoryItem dateItem = new DateHistoryItem(command.getHistoryId(), new DatePeriod(command.getStartDate(), command.getEndDate()!= null? command.getEndDate():  GeneralDate.max()));
			Optional<ShortWorkTimeHistory> existHist = sWorkTimeHistoryRepository.getBySid(companyId, command.getEmployeeId());
			// In case of exist history of this employee
			if (!existHist.isPresent()){
				throw new RuntimeException("Invalid ShortWorkTimeHistory");
			}
			
			Optional<DateHistoryItem> itemtoBeUpdated = existHist.get().getHistoryItems().stream().filter(hst->hst.identifier().equals(command.getHistoryId())).findFirst();
			if (!itemtoBeUpdated.isPresent()){
				throw new RuntimeException("Invalid ShortWorkTimeHistory");
			}
			
			GeneralDate endDate = (command.getEndDate() != null) ? command.getEndDate() : GeneralDate.ymd(9999, 12, 31);
			DatePeriod newSpan = new DatePeriod(command.getStartDate(), endDate);
			existHist.get().changeSpan(itemtoBeUpdated.get(), newSpan);
			
			sWorkTimeHistoryRepository.update(command.getEmployeeId(),dateItem);
		}
		ShortWorkTimeHistoryItem sWorkTime = new ShortWorkTimeHistoryItem(command);
		sWorkTimeHistItemRepository.update(sWorkTime);
		
	}

}
