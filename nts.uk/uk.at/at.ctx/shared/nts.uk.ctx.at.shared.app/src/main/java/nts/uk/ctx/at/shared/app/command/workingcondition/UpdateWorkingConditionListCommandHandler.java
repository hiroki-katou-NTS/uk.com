package nts.uk.ctx.at.shared.app.command.workingcondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateWorkingConditionListCommandHandler extends CommandHandlerWithResult<List<UpdateWorkingConditionCommand>, List<MyCustomizeException>>
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
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<UpdateWorkingConditionCommand>> context) {
		List<UpdateWorkingConditionCommand> cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		UpdateWorkingConditionCommand updateFirst = cmd.get(0);
		// sidsPidsMap
		List<String> sids = cmd.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		List<String> errorLst = new ArrayList<>();
		List<WorkingCondition> listHistBySids = new ArrayList<>();
		List<WorkingCondition> workingCondInserts = new ArrayList<>();
		List<WorkingConditionItem> workingCondItems = new ArrayList<>();
		List<MyCustomizeException> errorExceptionLst = new ArrayList<>();
		if (updateFirst.getStartDate() != null){
			List<WorkingCondition> listHistBySid =  workingConditionRepository.getBySidsAndCid(cid, sids);
			listHistBySids.addAll(listHistBySid);
		}
		cmd.stream().forEach(c -> {
			try {
				if (c.getStartDate() != null) {
					List<WorkingCondition> workingCondOpt = listHistBySids.stream()
							.filter(item -> item.getEmployeeId().equals(c.getEmployeeId()))
							.collect(Collectors.toList());
					if (CollectionUtil.isEmpty(workingCondOpt)) {
						errorLst.add(c.getEmployeeId());
						return;
					}
					WorkingCondition workingCond = workingCondOpt.get(0);
					Optional<DateHistoryItem> itemToBeUpdated = workingCond.getDateHistoryItem().stream()
							.filter(hist -> hist.identifier().equals(c.getHistId())).findFirst();
					if (!itemToBeUpdated.isPresent()) {
						errorLst.add(c.getEmployeeId());
					}
					GeneralDate endDate = c.getEndDate() != null ? c.getEndDate() : GeneralDate.max();
					workingCond.changeSpan(itemToBeUpdated.get(), new DatePeriod(c.getStartDate(), endDate));
					workingCondInserts.add(workingCond);
				}
				WokingConditionCommandCustom workingCondItem = updateWorkingConditionCommandAssembler.fromDTOCustom(c);
				if(CollectionUtil.isEmpty(workingCondItem.getEx())) {
					workingCondItems.add(workingCondItem.getWorkingConditionItem());
				}else {
					errorExceptionLst.addAll(workingCondItem.getEx());
				}
				
			} catch (BusinessException e) {
				MyCustomizeException ex = new MyCustomizeException(e.getMessageId(), Arrays.asList(c.getEmployeeId()));
				errorExceptionLst.add(ex);
			}

		});
		
		if(!workingCondInserts.isEmpty()) {
			workingConditionRepository.saveAll(workingCondInserts);
		}
		
		if(!workingCondItems.isEmpty()) {
			workingConditionItemRepository.updateAll(workingCondItems);
		}
		return errorExceptionLst;
		
	}

}
