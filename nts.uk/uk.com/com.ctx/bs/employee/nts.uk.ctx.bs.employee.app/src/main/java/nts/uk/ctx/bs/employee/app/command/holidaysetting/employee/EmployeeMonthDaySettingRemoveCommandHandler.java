package nts.uk.ctx.bs.employee.app.command.holidaysetting.employee;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year;
import nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmployeeMonthDaySettingRemoveCommandHandler.
 */
@Stateless
public class EmployeeMonthDaySettingRemoveCommandHandler extends CommandHandler<EmployeeMonthDaySettingRemoveCommand>{
	
	/** The repository. */
	@Inject
	private EmployeeMonthDaySettingRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<EmployeeMonthDaySettingRemoveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
		// Get command
		EmployeeMonthDaySettingRemoveCommand command = context.getCommand();
		
		// Remove
		this.repository.remove(new CompanyId(companyId), command.getSId(), new Year(command.getYear()));
	}

}
