package nts.uk.ctx.at.shared.app.command.holidaysetting.employment;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class EmploymentMonthDaySettingSaveCommandHandler.
 */
@Stateless
public class EmploymentMonthDaySettingSaveCommandHandler extends CommandHandler<EmploymentMonthDaySettingSaveCommand> {
	
	/** The repository. */
	@Inject
	private EmploymentMonthDaySettingRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<EmploymentMonthDaySettingSaveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
		// Get Command
		EmploymentMonthDaySettingSaveCommand command = context.getCommand();
		
		// convert to domain
		EmploymentMonthDaySetting domain = new EmploymentMonthDaySetting(command);
		
		Optional<EmploymentMonthDaySetting> optional = this.repository.findByYear(new CompanyId(companyId), command.getEmploymentCode(), new Year(command.getYear()));
	
		// save data
		if(optional.isPresent()){
			this.repository.remove(domain);
			this.repository.add(domain);
		} else {
			this.repository.add(domain);
		}
	}

}
