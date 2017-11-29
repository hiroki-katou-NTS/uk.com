package nts.uk.ctx.bs.employee.app.command.employment.history;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItem;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddEmploymentHistoryCommandHandler extends CommandHandlerWithResult<AddEmploymentHistoryCommand,PeregAddCommandResult>
implements PeregAddCommandHandler<AddEmploymentHistoryCommand> {

	@Inject
	private EmploymentHistoryRepository employmentHistoryRepository;
	@Inject
	private EmploymentHistoryItemRepository employmentHistoryItemRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00014";
	}

	@Override
	public Class<?> commandClass() {
		return AddEmploymentHistoryCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddEmploymentHistoryCommand> context) {
		val command = context.getCommand();
		String newHistID = IdentifierUtil.randomUniqueId();
		DateHistoryItem dateItem = new DateHistoryItem(newHistID, new DatePeriod(command.getStartDate(), command.getEndDate()));
		
		EmploymentHistory itemtoBeAdded = null;
		
		Optional<EmploymentHistory> histItem = employmentHistoryRepository.getByEmployeeId(command.getEmployeeId());
		if (histItem.isPresent()){
			itemtoBeAdded = histItem.get();
		} else {
			itemtoBeAdded = new EmploymentHistory(command.getEmployeeId(), new ArrayList<>());
		}
		itemtoBeAdded.add(dateItem);
		
		employmentHistoryRepository.add(itemtoBeAdded);
		
		EmploymentHistoryItem domain = EmploymentHistoryItem.createFromJavaType(newHistID, command.getEmployeeId(), command.getSalarySegment(), command.getEmploymentCode());
		employmentHistoryItemRepository.adḍ̣̣̣(domain);
		
		return new PeregAddCommandResult(newHistID);
	}

}
