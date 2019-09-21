package nts.uk.ctx.at.shared.app.command.workingcondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddWorkingConditionListCommandHandler extends CommandHandlerWithResult<List<AddWorkingConditionCommand>, List<MyCustomizeException>>
implements PeregAddListCommandHandler<AddWorkingConditionCommand>{
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	@Inject
	private WorkingConditionRepository workingConditionRepository;
	
	@Inject
	private AddWorkingConditionCommandAssembler addWorkingConditionCommandAssembler;
	@Override
	public String targetCategoryCd() {
		return "CS00020";
	}

	@Override
	public Class<?> commandClass() {
		return AddWorkingConditionCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<AddWorkingConditionCommand>> context) {
		List<AddWorkingConditionCommand> cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		
		Map<String, String> recordIds = new HashMap<>();
		List<MyCustomizeException> result = new ArrayList<>();
		List<WorkingCondition> workingCondInserts = new ArrayList<>();
		List<WorkingConditionItem> workingCondItems = new ArrayList<>();
		// sidsPidsMap
		List<String> sids = cmd.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		List<WorkingCondition> listHistBySid =  workingConditionRepository.getBySidsAndCid(cid, sids);

		
		cmd.stream().forEach(c ->{
			try {
				String histId = IdentifierUtil.randomUniqueId();
				WorkingCondition workingCond = new WorkingCondition(cid, c.getEmployeeId(),new ArrayList<DateHistoryItem>());
				List<WorkingCondition> workingConditionOpt = listHistBySid.stream().filter(item -> item.getEmployeeId().equals(c.getEmployeeId())).collect(Collectors.toList());
				if(!CollectionUtil.isEmpty(workingConditionOpt)) {
					workingCond = workingConditionOpt.get(0);
				}
				GeneralDate endDate = c.getEndDate() !=null? c.getEndDate() : GeneralDate.max();
				DateHistoryItem itemToBeAdded = new DateHistoryItem(histId, new DatePeriod(c.getStartDate(), endDate));
				workingCond.add(itemToBeAdded);
				workingCondInserts.add(workingCond);
				WorkingConditionItem  workingCondItem = addWorkingConditionCommandAssembler.fromDTO(histId, c);
				workingCondItems.add(workingCondItem);
				recordIds.put(c.getEmployeeId(), histId);
				
			}catch(BusinessException  e) {
				MyCustomizeException ex = new MyCustomizeException(e.getMessageId(), Arrays.asList(c.getEmployeeId()));
				result.add(ex);	
			}

		});
		
		if(!workingCondInserts.isEmpty()) {
			workingConditionRepository.saveAll(workingCondInserts);
		}
		
		if(!workingCondItems.isEmpty()) {
			workingConditionItemRepository.addAll(workingCondItems);
		}
		
		if(!recordIds.isEmpty()) {
			result.add(new MyCustomizeException("NOERROR", recordIds));
		}
		
		return result;
	}

}
