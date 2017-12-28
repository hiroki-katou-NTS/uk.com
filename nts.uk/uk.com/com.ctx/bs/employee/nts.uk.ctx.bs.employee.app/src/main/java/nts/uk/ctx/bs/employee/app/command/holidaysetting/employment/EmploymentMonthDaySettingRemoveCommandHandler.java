package nts.uk.ctx.bs.employee.app.command.holidaysetting.employment;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year;
import nts.uk.ctx.bs.employee.dom.holidaysetting.employment.EmploymentMonthDaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmploymentMonthDaySettingRemoveCommandHandler.
 */
@Stateless
public class EmploymentMonthDaySettingRemoveCommandHandler extends CommandHandler<EmploymentMonthDaySettingRemoveCommand>{
	
	/** The repository. */
	@Inject
	private EmploymentMonthDaySettingRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<EmploymentMonthDaySettingRemoveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
		// Get command
		EmploymentMonthDaySettingRemoveCommand command = context.getCommand();
		
		// Remove
		this.repository.remove(new CompanyId(companyId), command.getEmpCd(), new Year(command.getYear()));
	}

}
