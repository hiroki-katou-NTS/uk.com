package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.event.SpecialHolidayDomainEvent;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author tanlv
 *
 */
@Stateless
public class DeleteSpecialHolidayCommandHandler extends CommandHandler<SpecialHolidayDeleteCommand> {
	@Inject
	private SpecialHolidayRepository sphdRepo;

	@Override
	protected void handle(CommandHandlerContext<SpecialHolidayDeleteCommand> context) {
		SpecialHolidayDeleteCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Integer sHECD = command.getSpecialHolidayCode();
		// call event
		Optional<SpecialHoliday> oldDomain =  sphdRepo.findByCode(companyId, sHECD);
		if(oldDomain.isPresent()){
			SpecialHolidayDomainEvent sHC = SpecialHolidayDomainEvent.createFromDomain(false,oldDomain.get());
			sHC.toBePublished();
		}
		// Delete Special Holiday

		sphdRepo.delete(companyId, sHECD);

//		sphdRepo.delete(companyId, command.getSpecialHolidayCode());
//		
//		SpecialHoliday sphd = new SpecialHoliday();
//		sphd.publishEvent(false, command.getSpecialHolidayCode(), "");
	}
}
