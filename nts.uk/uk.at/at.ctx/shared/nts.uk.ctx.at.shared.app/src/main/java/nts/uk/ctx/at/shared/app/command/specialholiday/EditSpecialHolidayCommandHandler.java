package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */
@Transactional
@Stateless
public class EditSpecialHolidayCommandHandler extends CommandHandlerWithResult<SpecialHolidayCommand, List<String>> {
	@Inject
	private SpecialHolidayRepository sphdRepo;
	
	@Override
	protected List<String> handle(CommandHandlerContext<SpecialHolidayCommand> context) {
		SpecialHolidayCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		List<String> errList = new ArrayList<String>();
		
		SpecialHoliday domain = command.toDomain(companyId);
		errList.addAll(domain.getGrantPeriodic().validateInput());
		errList.addAll(domain.getSpecialLeaveRestriction().validateInput());
		
		if (errList.isEmpty()) {
			sphdRepo.update(domain);
			
			domain.publishEvent(true);
		}
		
		return errList;
	}
}
