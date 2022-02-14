package nts.uk.ctx.at.shared.app.command.specialholiday;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.event.SpecialHolidayDomainEvent;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
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
	
	@Inject
	private ElapseYearRepository elapseYearRepository;
	
	@Inject
	private GrantDateTblRepository grantDateTblRepository;

	@Override
	protected void handle(CommandHandlerContext<SpecialHolidayDeleteCommand> context) {
		SpecialHolidayDeleteCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Integer sHECD = command.getSpecialHolidayCode();
		// call event
		Optional<SpecialHoliday> oldDomain =  sphdRepo.findBySingleCD(companyId, sHECD);
		if(oldDomain.isPresent()){
			SpecialHolidayDomainEvent sHC = SpecialHolidayDomainEvent.createFromDomain(false,oldDomain.get());
			sHC.toBePublished();
		}
		// Delete Special Holiday

		sphdRepo.delete(companyId, sHECD);
		elapseYearRepository.delete(companyId, sHECD);
		grantDateTblRepository.delete(companyId, sHECD, command.getGrantDateCode());

//		sphdRepo.delete(companyId, command.getSpecialHolidayCode());
//		
//		SpecialHoliday sphd = new SpecialHoliday();
//		sphd.publishEvent(false, command.getSpecialHolidayCode(), "");
	}
}
