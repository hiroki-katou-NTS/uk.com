package nts.uk.ctx.at.shared.app.command.shortworktime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistItemRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.SWorkTimeHistoryRepository;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistory;
import nts.uk.ctx.at.shared.dom.shortworktime.ShortWorkTimeHistoryItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateShortWorkTimeListCommandHandler extends CommandHandlerWithResult<List<UpdateShortWorkTimeCommand>, List<MyCustomizeException>>
implements PeregUpdateListCommandHandler<UpdateShortWorkTimeCommand>{
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
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<UpdateShortWorkTimeCommand>> context) {
		List<UpdateShortWorkTimeCommand> cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		// sidsPidsMap
		List<String> sids = cmd.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		UpdateShortWorkTimeCommand updateFirst = cmd.get(0);
		List<ShortWorkTimeHistory> existHistMaps = new ArrayList<>();
		List<ShortWorkTimeHistoryItem> histItems = new ArrayList<>();
		List<String> errorLst = new ArrayList<>();
		Map<String, DateHistoryItem> dateHistItems = new HashMap<>();
		List<MyCustomizeException> errorExceptionLst = new ArrayList<>();
		
		if(updateFirst != null) {
			List<ShortWorkTimeHistory> existHistMap = sWorkTimeHistoryRepository.getBySidsAndCid(cid, sids);
			existHistMaps.addAll(existHistMap);
		}
		
		cmd.stream().forEach(c ->{
			try {
				if(c.getStartDate() != null) {
					DateHistoryItem dateItem = new DateHistoryItem(c.getHistoryId(), new DatePeriod(c.getStartDate(), c.getEndDate()!= null? c.getEndDate():  GeneralDate.max()));
					Optional<ShortWorkTimeHistory> existHistOpt = existHistMaps.stream()
							.filter(item -> item.getEmployeeId().equals(c.getEmployeeId())).findFirst();						
					if (!existHistOpt.isPresent()) {
						errorLst.add(c.getEmployeeId());
						return;
					}
					Optional<DateHistoryItem> itemToBeUpdate = existHistOpt.get().getHistoryItems().stream()
							.filter(h -> h.identifier().equals(c.getHistoryId())).findFirst();
					if (!itemToBeUpdate.isPresent()){
						errorLst.add(c.getEmployeeId());
						return;
					}
					GeneralDate endDate = (c.getEndDate() != null) ? c.getEndDate() : GeneralDate.ymd(9999, 12, 31);
					DatePeriod newSpan = new DatePeriod(c.getStartDate(), endDate);
					existHistOpt.get().changeSpan(itemToBeUpdate.get(), newSpan);
					dateHistItems.put(c.getEmployeeId(), dateItem);
				}
				ShortWorkTimeHistoryItem sWorkTime = new ShortWorkTimeHistoryItem(c);
				histItems.add(sWorkTime);
				
			}catch(BusinessException e) {
				MyCustomizeException ex = new MyCustomizeException(e.getMessageId(), Arrays.asList(c.getEmployeeId()), "ki");
				errorExceptionLst.add(ex);
			}

		});
		
		if(!dateHistItems.isEmpty()) {
			sWorkTimeHistoryRepository.updateAll(dateHistItems);
		}
		
		if(!histItems.isEmpty()) {
			sWorkTimeHistItemRepository.updateAll(histItems);
		}
		if (errorLst.size() > 0) {
			errorExceptionLst.add(new MyCustomizeException("Msg_345", errorLst));
		}
		return errorExceptionLst;
	}

}
