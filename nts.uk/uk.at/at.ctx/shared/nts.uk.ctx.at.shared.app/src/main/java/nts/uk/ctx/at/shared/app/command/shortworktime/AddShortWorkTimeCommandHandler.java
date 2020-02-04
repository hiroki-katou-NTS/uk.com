package nts.uk.ctx.at.shared.app.command.shortworktime;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistoryRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddShortWorkTimeCommandHandler extends CommandHandlerWithResult<AddShortWorkTimeCommand, PeregAddCommandResult>
	implements PeregAddCommandHandler<AddShortWorkTimeCommand>{
	
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
		return AddShortWorkTimeCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddShortWorkTimeCommand> context) {
		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		command.setHistoryId(IdentifierUtil.randomUniqueId());
		DateHistoryItem dateItem = new DateHistoryItem(command.getHistoryId(), new DatePeriod(command.getStartDate()!=null?command.getStartDate():GeneralDate.min(), command.getEndDate()!= null? command.getEndDate():  GeneralDate.max()));
		Optional<ShortWorkTimeHistory> existHist = sWorkTimeHistoryRepository.getBySid(companyId, command.getEmployeeId());
		
		ShortWorkTimeHistory itemtoBeAdded = new ShortWorkTimeHistory(companyId, command.getEmployeeId(),new ArrayList<>());
		// In case of exist history of this employee
		if (existHist.isPresent()){
			itemtoBeAdded = existHist.get();
		}
		
		itemtoBeAdded.add(dateItem);
		
		sWorkTimeHistoryRepository.add(companyId, command.getEmployeeId(), dateItem);
		
		ShortWorkTimeHistoryItem sWorkTime = new ShortWorkTimeHistoryItem(command);
		sWorkTimeHistItemRepository.add(sWorkTime);
		
		return new PeregAddCommandResult(command.getHistoryId());
	}

}
