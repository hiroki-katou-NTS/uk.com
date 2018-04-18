package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class YearHolidayGrantDeleteCommandHandler.
 */

@Stateless
public class YearHolidayGrantDeleteCommandHandler extends CommandHandler<YearHolidayGrantDeleteCommand> {
	@Inject
	private LengthServiceRepository lengthServiceRepository;
	
	@Inject
	private YearHolidayRepository yearHolidayRepo;

	@Override
	protected void handle(CommandHandlerContext<YearHolidayGrantDeleteCommand> context) {
		YearHolidayGrantDeleteCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// check exists code
		Optional<GrantHdTblSet> grantHolidaySet = yearHolidayRepo.findByCode(companyId, command.getYearHolidayCode());
		if (!grantHolidaySet.isPresent()) {
			throw new RuntimeException("Year Holiday Not Found");
		}
				
		// remove all length of service
		lengthServiceRepository.remove(companyId, command.getYearHolidayCode());
		// remove year holiday by code
		yearHolidayRepo.remove(companyId, command.getYearHolidayCode());
		// remove conditions
		yearHolidayRepo.removeCondition(companyId, command.getYearHolidayCode());
		// remove grant date by condition no
		yearHolidayRepo.removeGrantDates(companyId, command.getYearHolidayCode());
	}
}
