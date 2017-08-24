package nts.uk.ctx.at.shared.app.command.yearholidaygrant;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

@Transactional
@Stateless
public class GrantHolidayTblAddCommandHandler extends CommandHandler<List<GrantHolidayTblCommand>> {
	@Inject
	private GrantYearHolidayRepository grantYearHolidayRepo;
	
	@Override
	protected void handle(CommandHandlerContext<List<GrantHolidayTblCommand>> context) {
		List<GrantHolidayTblCommand> command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		List<GrantHdTbl> grantHolidays = command.stream()
				.filter(x -> (x.getLengthOfServiceYears() != null && x.getLengthOfServiceYears() != 0) 
						|| (x.getLengthOfServiceMonths() != null && x.getLengthOfServiceMonths() != 0) 
						|| (x.getGrantDays() != null))
				.map(x->x.toDomain(companyId)).collect(Collectors.toList());
		GrantHdTbl.validateInput(grantHolidays);
		
		for (GrantHdTbl item : grantHolidays) {
			// validate
			item.validate();		
					
			Optional<GrantHdTbl> garntHd = grantYearHolidayRepo.find(companyId, item.getConditionNo(), item.getYearHolidayCode().v(), item.getGrantYearHolidayNo());
			if (garntHd.isPresent()) {
				// update
				grantYearHolidayRepo.update(item);
			} else {
				// add to db		
				grantYearHolidayRepo.add(item);
			}
		}
	}
}
