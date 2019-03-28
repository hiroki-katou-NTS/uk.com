package nts.uk.ctx.bs.employee.app.command.employment.history;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryIntermediate;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryService;
import nts.uk.ctx.bs.person.dom.person.common.ConstantUtils;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateEmploymentHistoryListCommandHandler extends CommandHandler<List<UpdateEmploymentHistoryCommand>>
implements PeregUpdateListCommandHandler<UpdateEmploymentHistoryCommand>{

	@Inject
	private EmploymentHistoryRepository employmentHistoryRepository;
	@Inject
	private EmploymentHistoryItemRepository employmentHistoryItemRepository;
	@Inject
	private EmploymentHistoryService employmentHistoryService;
	
	@Override
	public String targetCategoryCd() {
		return "CS00014";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateEmploymentHistoryCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<List<UpdateEmploymentHistoryCommand>> context) {
		List<UpdateEmploymentHistoryCommand> command = context.getCommand();
		String cid = AppContexts.user().companyId();
		// sidsPidsMap
		List<String> sids = command.parallelStream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		List<EmploymentHistoryIntermediate> domainIntermediates = new ArrayList<>();
		List<EmploymentHistoryItem> employmentHistoryItems = new ArrayList<>();
			Map<String, List<EmploymentHistory>> existHistMap = employmentHistoryRepository.getAllByCidAndSids(cid, sids).parallelStream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));
			command.parallelStream().forEach(c ->{
				// In case of date period are exist in the screen,
				if(c.getStartDate() != null) {
					List<EmploymentHistory> existHistLst = existHistMap.get(c.getEmployeeId());
					if(existHistLst != null) {
						Optional<DateHistoryItem> itemToBeUpdate = existHistLst.get(0).getHistoryItems().stream()
								.filter(h -> h.identifier().equals(c.getHistoryId())).findFirst();
						if(itemToBeUpdate.isPresent()) {
							existHistLst.get(0).changeSpan(itemToBeUpdate.get(), new DatePeriod(c.getStartDate(), c.getEndDate()!= null? c.getEndDate():  ConstantUtils.maxDate()));
							domainIntermediates.add(new EmploymentHistoryIntermediate(existHistLst.get(0), itemToBeUpdate.get()));
						}
					}	
				}
				
				// Update detail table
				EmploymentHistoryItem histItem = EmploymentHistoryItem.createFromJavaType(c.getHistoryId(),
						c.getEmployeeId(), c.getEmploymentCode(),
						c.getSalarySegment() != null ? c.getSalarySegment().intValue() : null);
				employmentHistoryItems.add(histItem);
			});
		
		if(!domainIntermediates.isEmpty()) {
			employmentHistoryService.updateAll(domainIntermediates);
		}
		
		if(!employmentHistoryItems.isEmpty()) {
			employmentHistoryItemRepository.updateAll(employmentHistoryItems);
		}
	}

}
