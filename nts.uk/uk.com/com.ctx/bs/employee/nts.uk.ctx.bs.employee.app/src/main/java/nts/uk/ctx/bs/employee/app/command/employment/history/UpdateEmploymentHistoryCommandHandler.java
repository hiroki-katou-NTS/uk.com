package nts.uk.ctx.bs.employee.app.command.employment.history;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryService;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateEmploymentHistoryCommandHandler extends CommandHandler<UpdateEmploymentHistoryCommand>
	implements PeregUpdateCommandHandler<UpdateEmploymentHistoryCommand>{
	
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
	protected void handle(CommandHandlerContext<UpdateEmploymentHistoryCommand> context) {
		val command = context.getCommand();
		Optional<EmploymentHistory> existHist = employmentHistoryRepository.getByEmployeeId(command.getEmployeeId());
		if (!existHist.isPresent()){
			throw new RuntimeException("invalid employmentHistory"); 
		}
			
		Optional<DateHistoryItem> itemToBeUpdate = existHist.get().getHistoryItems().stream()
                .filter(h -> h.identifier().equals(command.getHistoryId()))
                .findFirst();
		
		if (!itemToBeUpdate.isPresent()){
			throw new RuntimeException("invalid employmentHistory");
		}
		existHist.get().changeSpan(itemToBeUpdate.get(), new DatePeriod(command.getStartDate(), command.getEndDate()));
		employmentHistoryService.update(existHist.get(), itemToBeUpdate.get());
		
		// Update detail table
		EmploymentHistoryItem histItem = EmploymentHistoryItem.createFromJavaType(command.getHistoryId(), command.getEmployeeId(), command.getSalarySegment()!= null?command.getSalarySegment().intValue():0, command.getEmploymentCode());
		employmentHistoryItemRepository.update(histItem);
	}

}
