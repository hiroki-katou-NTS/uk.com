package nts.uk.ctx.bs.employee.app.command.holidaysetting.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year;
import nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySetting;
import nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmployeeMonthDaySettingSaveCommandHandler.
 */
@Stateless
public class EmployeeMonthDaySettingSaveCommandHandler extends CommandHandler<EmployeeMonthDaySettingSaveCommand> {
	
	/** The repository. */
	@Inject
	private EmployeeMonthDaySettingRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<EmployeeMonthDaySettingSaveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
		// Get Command
		EmployeeMonthDaySettingSaveCommand command = context.getCommand();
		
		// convert to domain
		EmployeeMonthDaySetting domain = new EmployeeMonthDaySetting(command);
		
		Optional<EmployeeMonthDaySetting> optional = this.repository.findByYear(new CompanyId(companyId), command.getSId(), new Year(command.getYear()));
	
		// save data
		if(optional.isPresent()){
			this.repository.update(domain);
		} else {
			this.repository.add(domain);
		}
	}

}
