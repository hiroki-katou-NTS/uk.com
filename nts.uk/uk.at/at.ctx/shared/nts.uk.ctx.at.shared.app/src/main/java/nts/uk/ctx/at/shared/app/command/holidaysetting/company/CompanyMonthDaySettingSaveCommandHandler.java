package nts.uk.ctx.at.shared.app.command.holidaysetting.company;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySettingRepository;
import nts.uk.shr.com.context.AppContexts;


/**
 * The Class CompanyMonthDaySettingSaveCommandHandler.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
			this.repository.remove(domain);
			this.repository.add(domain);
		} else {
			this.repository.add(domain);
		}
		
	}

}
