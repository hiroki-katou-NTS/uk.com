package nts.uk.ctx.at.shared.app.command.workingcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddWorkingConditionListCommandHandler extends CommandHandlerWithResult<List<AddWorkingConditionCommand>, List<PeregAddCommandResult>>
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
	protected List<PeregAddCommandResult> handle(CommandHandlerContext<List<AddWorkingConditionCommand>> context) {
		List<AddWorkingConditionCommand> cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		// sidsPidsMap
		List<String> sids = cmd.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		
		List<WorkingCondition> listHistBySid =  workingConditionRepository.getBySidsAndCid(cid, sids);
		List<WorkingCondition> workingCondInserts = new ArrayList<>();
		List<WorkingConditionItem> workingCondItems = new ArrayList<>();
		List<PeregAddCommandResult> result = new ArrayList<>();
		cmd.stream().forEach(c ->{
			String histId = IdentifierUtil.randomUniqueId();
			WorkingCondition workingCond = new WorkingCondition(cid, c.getEmployeeId(),new ArrayList<DateHistoryItem>());
			Optional<WorkingCondition> workingConditionOpt = listHistBySid.stream().filter(item -> item.getEmployeeId().equals(c.getEmployeeId())).findFirst();
			if(workingConditionOpt.isPresent()) {
				workingCond = workingConditionOpt.get();
			}
			GeneralDate endDate = c.getEndDate() !=null? c.getEndDate() : GeneralDate.max();
			DateHistoryItem itemToBeAdded = new DateHistoryItem(histId, new DatePeriod(c.getStartDate(), endDate));
			workingCond.add(itemToBeAdded);
			workingCondInserts.add(workingCond);
			WorkingConditionItem  workingCondItem = addWorkingConditionCommandAssembler.fromDTO(histId, c);
			workingCondItems.add(workingCondItem);
			result.add(new PeregAddCommandResult(histId));
		});
		
		if(!workingCondInserts.isEmpty()) {
			workingConditionRepository.saveAll(workingCondInserts);
		}
		
		if(!workingCondItems.isEmpty()) {
			workingConditionItemRepository.addAll(workingCondItems);
		}
		return result;
	}

}
