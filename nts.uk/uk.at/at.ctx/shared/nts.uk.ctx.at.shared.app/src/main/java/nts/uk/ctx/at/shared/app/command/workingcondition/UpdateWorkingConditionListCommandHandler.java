package nts.uk.ctx.at.shared.app.command.workingcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateWorkingConditionListCommandHandler extends CommandHandler<List<UpdateWorkingConditionCommand>>
implements PeregUpdateListCommandHandler<UpdateWorkingConditionCommand>{
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	@Inject
	private WorkingConditionRepository workingConditionRepository;
	
	@Inject
	private UpdateWorkingConditionCommandAssembler updateWorkingConditionCommandAssembler;
	
	@Override
	public String targetCategoryCd() {
		return "CS00020";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateWorkingConditionCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<List<UpdateWorkingConditionCommand>> context) {
		List<UpdateWorkingConditionCommand> cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		UpdateWorkingConditionCommand updateFirst = cmd.get(0);
		// sidsPidsMap
		List<String> sids = cmd.parallelStream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		List<String> errorLst = new ArrayList<>();
		List<WorkingCondition> listHistBySids = new ArrayList<>();
		List<WorkingCondition> workingCondInserts = new ArrayList<>();
		List<WorkingConditionItem> workingCondItems = new ArrayList<>();
		if (updateFirst.getStartDate() != null){
			List<WorkingCondition> listHistBySid =  workingConditionRepository.getBySidsAndCid(cid, sids);
			listHistBySids.addAll(listHistBySid);
		}
		cmd.parallelStream().forEach(c ->{
			if(c.getStartDate() != null) {
				Optional<WorkingCondition> workingCondOpt =  listHistBySids.parallelStream().filter(item -> item.getEmployeeId().equals(c.getEmployeeId())).findFirst();
				if(!workingCondOpt.isPresent()) {
					errorLst.add(c.getEmployeeId());
				}
				WorkingCondition workingCond = workingCondOpt.get();
				Optional<DateHistoryItem> itemToBeUpdated = workingCond.getDateHistoryItem().stream().filter(hist->hist.identifier().equals(c.getHistId())).findFirst();
				if (!itemToBeUpdated.isPresent()){
					errorLst.add(c.getEmployeeId());
				}
				GeneralDate endDate = c.getEndDate() !=null? c.getEndDate() : GeneralDate.max();
				workingCond.changeSpan(itemToBeUpdated.get(), new DatePeriod(c.getStartDate(), endDate));
				workingCondInserts.add(workingCond);
			}
			WorkingConditionItem  workingCondItem = updateWorkingConditionCommandAssembler.fromDTO(c);
			workingCondItems.add(workingCondItem);
		});	
		
		if(!workingCondInserts.isEmpty()) {
			workingConditionRepository.saveAll(workingCondInserts);
		}
		
		if(!workingCondItems.isEmpty()) {
			workingConditionItemRepository.updateAll(workingCondItems);
		}
		
	}

}
