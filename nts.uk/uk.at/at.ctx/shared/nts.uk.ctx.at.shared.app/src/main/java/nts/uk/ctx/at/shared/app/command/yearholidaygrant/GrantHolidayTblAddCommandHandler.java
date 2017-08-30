package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author TanLV
 *
 */
@Transactional
@Stateless
public class GrantHolidayTblAddCommandHandler extends CommandHandler<GrantHolidayTblCommand> {
	@Inject
	private GrantYearHolidayRepository grantYearHolidayRepo;
	
	@Override
	protected void handle(CommandHandlerContext<GrantHolidayTblCommand> context) {
		GrantHolidayTblCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		List<GrantHdTbl> grantHolidays = command.getGrantHolidayList().stream()
				.filter(x -> (x.getLengthOfServiceYears() != null && x.getLengthOfServiceYears() != 0) 
						|| (x.getLengthOfServiceMonths() != null && x.getLengthOfServiceMonths() != 0) 
						|| (x.getGrantDays() != null))
				.map(x->x.toDomain(companyId)).collect(Collectors.toList());
		GrantHdTbl.validateInput(grantHolidays);
		
		// remove all
		grantYearHolidayRepo.remove(companyId, command.getConditionNo(), command.getYearHolidayCode());
		
		for (GrantHdTbl item : grantHolidays) {
			// validate
			item.validate();		
					
			// add to db		
			grantYearHolidayRepo.add(item);
		}
	}
}
