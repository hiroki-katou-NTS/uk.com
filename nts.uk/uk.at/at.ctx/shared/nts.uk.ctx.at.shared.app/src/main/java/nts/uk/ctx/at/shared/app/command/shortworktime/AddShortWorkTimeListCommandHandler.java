package nts.uk.ctx.at.shared.app.command.shortworktime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddShortWorkTimeListCommandHandler extends CommandHandlerWithResult<List<AddShortWorkTimeCommand>, List<PeregAddCommandResult>>
implements PeregAddListCommandHandler<AddShortWorkTimeCommand>{
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
	protected List<PeregAddCommandResult> handle(CommandHandlerContext<List<AddShortWorkTimeCommand>> context) {
		List<AddShortWorkTimeCommand> cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		List<PeregAddCommandResult> result =  new ArrayList<>();
		Map<String, DateHistoryItem> histItemMap = new HashMap<>();
		List<ShortWorkTimeHistoryItem> histItems = new ArrayList<>();
		// sidsPidsMap
		List<String> sids = cmd.parallelStream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		Map<String, List<ShortWorkTimeHistory>> existHistMap = sWorkTimeHistoryRepository.getBySidsAndCid(cid, sids).parallelStream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));
		cmd.parallelStream().forEach(c ->{
			String newHist = IdentifierUtil.randomUniqueId();
			c.setHistoryId(newHist);
			DateHistoryItem dateItem = new DateHistoryItem(c.getHistoryId(), new DatePeriod(c.getStartDate()!=null?c.getStartDate():GeneralDate.min(), c.getEndDate()!= null? c.getEndDate():  GeneralDate.max()));
			ShortWorkTimeHistory itemtoBeAdded = new ShortWorkTimeHistory(cid, c.getEmployeeId(),new ArrayList<>());
			List<ShortWorkTimeHistory> shortWorkTimeHistLst = existHistMap.get(c.getEmployeeId());
			if(shortWorkTimeHistLst != null) {
				itemtoBeAdded = shortWorkTimeHistLst.get(0);
			}
			itemtoBeAdded.add(dateItem);
			histItemMap.put(c.getEmployeeId(), dateItem);
			ShortWorkTimeHistoryItem sWorkTime = new ShortWorkTimeHistoryItem(c);
			histItems.add(sWorkTime);
			result.add(new PeregAddCommandResult(newHist));
		});
		
		if(!histItemMap.isEmpty()) {
			sWorkTimeHistoryRepository.addAll(histItemMap);
		}
		
		if(!histItems.isEmpty()) {
			sWorkTimeHistItemRepository.addAll(histItems);
		}
		return result;
	}

}
