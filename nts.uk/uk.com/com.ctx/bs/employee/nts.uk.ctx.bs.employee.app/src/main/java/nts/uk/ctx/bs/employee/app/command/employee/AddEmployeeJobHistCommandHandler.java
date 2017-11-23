package nts.uk.ctx.bs.employee.app.command.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistory;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddEmployeeJobHistCommandHandler extends CommandHandlerWithResult<AddEmployeeCommand,PeregAddCommandResult>
	implements PeregAddCommandHandler<AddEmployeeCommand>{
	
	@Inject
	private JobEntryHistoryRepository jobEntryHistoryRepository;
	@Override
	public String targetCategoryCd() {
		return "CS00002";
	}

	@Override
	public Class<?> commandClass() {
		return AddEmployeeCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddEmployeeCommand> context) {
		
		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		
		JobEntryHistory jobEntry = JobEntryHistory.createFromJavaType(companyId, command.getSId(), command.getHiringType(), command.getRetirementDate(), command.getJoinDate(), command.getAdoptDate());
		
		jobEntryHistoryRepository.addJobEntryHistory(jobEntry);
		
		return new PeregAddCommandResult(command.getSId());
		
		
	}

}
