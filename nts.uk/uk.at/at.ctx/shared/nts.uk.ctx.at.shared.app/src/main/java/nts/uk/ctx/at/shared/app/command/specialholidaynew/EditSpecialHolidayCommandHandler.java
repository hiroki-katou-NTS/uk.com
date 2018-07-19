package nts.uk.ctx.at.shared.app.command.specialholidaynew;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

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
public class EditSpecialHolidayCommandHandler extends CommandHandler<SpecialHolidayCommand> {
	@Inject
	private SpecialHolidayRepository sphdRepo;
	
	@Override
	protected void handle(CommandHandlerContext<SpecialHolidayCommand> context) {
		SpecialHolidayCommand command = context.getCommand();
		
		SpecialHoliday domain = command.toDomain();
		domain.validate();
		
		// add to db		
		sphdRepo.update(domain);
	}
}
