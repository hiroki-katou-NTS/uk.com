package nts.uk.ctx.bs.employee.app.command.holidaysetting.company;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.common.CompanyId;
import nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year;
import nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySetting;
import nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySettingRepository;
import nts.uk.shr.com.context.AppContexts;


/**
 * The Class CompanyMonthDaySettingSaveCommandHandler.
 */
@Stateless
public class CompanyMonthDaySettingSaveCommandHandler extends CommandHandler<CompanyMonthDaySettingSaveCommand> {
	
	/** The repository. */
	@Inject
	private CompanyMonthDaySettingRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<CompanyMonthDaySettingSaveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
		// Get Command
		CompanyMonthDaySettingSaveCommand command = context.getCommand();
		
		// convert to domain
		CompanyMonthDaySetting domain = new CompanyMonthDaySetting(command);
		
		Optional<CompanyMonthDaySetting> optional = this.repository.findByYear(new CompanyId(companyId), new Year(command.getYear()));
	
		// save data
		if(optional.isPresent()){
			this.repository.update(domain);
		} else {
			this.repository.add(domain);
		}
		
	}

}
