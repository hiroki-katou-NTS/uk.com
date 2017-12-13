package nts.uk.ctx.bs.employee.app.command.department;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.department.CurrentAffiDept;
import nts.uk.ctx.bs.employee.dom.department.CurrentAffiDeptRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddCurrentAffiDeptCommandHandler extends CommandHandlerWithResult<AddCurrentAffiDeptCommand,PeregAddCommandResult>
	implements PeregAddCommandHandler<AddCurrentAffiDeptCommand>{

	@Inject
	private CurrentAffiDeptRepository currentAffiDeptRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00012";
	}

	@Override
	public Class<?> commandClass() {
		return AddCurrentAffiDeptCommand.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddCurrentAffiDeptCommand> context) {
		val command = context.getCommand();
		
		String newHistId = IdentifierUtil.randomUniqueId();
		
		DateHistoryItem histItem =new DateHistoryItem(newHistId, new DatePeriod(command.getStartDate(), command.getEndDate()));
		
		CurrentAffiDept domain = new CurrentAffiDept(command.getEmployeeId(), command.getAffiDeptId(), command.getDepartmentId(), new ArrayList<>());
		domain.add(histItem);
		
		currentAffiDeptRepository.addCurrentAffiDept(domain);
		
		return new PeregAddCommandResult(newHistId);
	}

}
