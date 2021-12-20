package nts.uk.ctx.at.shared.app.command.holidaysetting.workplace;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplaceMonthDaySettingSaveCommandHandler.
 */
@Stateless
public class WorkplaceMonthDaySettingSaveCommandHandler extends CommandHandler<WorkplaceMonthDaySettingSaveCommand> {
	
	/** The repository. */
	@Inject
	private WorkplaceMonthDaySettingRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkplaceMonthDaySettingSaveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();
		
		// Get Command
		WorkplaceMonthDaySettingSaveCommand command = context.getCommand();
		
		// convert to domain
		WorkplaceMonthDaySetting domain = new WorkplaceMonthDaySetting(command);
		
		Optional<WorkplaceMonthDaySetting> optional = this.repository.findByYear(new CompanyId(companyId), command.getWorkplaceID(), new Year(command.getYear()));
	
		// save data
		if(optional.isPresent()){
			this.repository.remove(domain);
			this.repository.add(domain);
		} else {
			this.repository.add(domain);
		}
	}

}
