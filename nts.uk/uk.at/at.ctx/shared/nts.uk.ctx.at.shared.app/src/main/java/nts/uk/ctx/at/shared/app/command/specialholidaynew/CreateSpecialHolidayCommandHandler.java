package nts.uk.ctx.at.shared.app.command.specialholidaynew;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholidaynew.SpecialHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */
@Transactional
@Stateless
public class CreateSpecialHolidayCommandHandler extends CommandHandler<SpecialHolidayCommand> {
	@Inject
	private SpecialHolidayRepository sphdRepo;
	
	@Override
	protected void handle(CommandHandlerContext<SpecialHolidayCommand> context) {
		SpecialHolidayCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// check exists code
		Optional<SpecialHoliday> specialHoliday = sphdRepo.findByCode(companyId, command.getSpecialHolidayCode());
		if (specialHoliday.isPresent()) {
			throw new BusinessException("Msg_3");
		}
		
		SpecialHoliday domain = command.toDomain(companyId);
		domain.validate();
		
		// add to db		
		sphdRepo.add(domain);
	}
}
