package nts.uk.ctx.bs.employee.app.command.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistory;
import nts.uk.ctx.bs.employee.dom.employeeinfo.JobEntryHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateEmployeeJobHistCommandHandler extends CommandHandler<UpdateEmployeeCommand>
	implements PeregUpdateCommandHandler<UpdateEmployeeCommand>{
	
	@Inject
	private JobEntryHistoryRepository jobEntryHistoryRepository;
	@Override
	public String targetCategoryCd() {
		return "CS00002";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateEmployeeCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateEmployeeCommand> context) {
		
		val command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		JobEntryHistory jobEntry = JobEntryHistory.createFromJavaType(companyId, command.getSId(), command.getHiringType(), command.getRetirementDate(), command.getJoinDate(), command.getAdoptDate());
		
		jobEntryHistoryRepository.updateJobEntryHistory(jobEntry);
	}

}
