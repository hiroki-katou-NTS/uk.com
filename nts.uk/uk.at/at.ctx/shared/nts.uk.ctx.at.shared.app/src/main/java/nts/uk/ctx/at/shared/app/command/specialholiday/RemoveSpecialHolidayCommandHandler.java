package nts.uk.ctx.at.shared.app.command.specialholiday;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.event.SpecialHolidayEvent;
import nts.uk.shr.com.context.AppContexts;

/**
 * The class RemoveSpecialHolidayCommandHandler
 * @author phongtq
 *
 */
@Stateless
public class RemoveSpecialHolidayCommandHandler extends CommandHandler<RemoveSpecialHolidayCommand> {
	/** The Respository */
	@Inject
	private SpecialHolidayRepository specialHolidayRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RemoveSpecialHolidayCommand> context) {
		RemoveSpecialHolidayCommand removeSpecialHolidayCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// Delete Special Holiday
		specialHolidayRepository.delete(companyId, removeSpecialHolidayCommand.getSpecialHolidayCode());
		SpecialHolidayEvent event = new SpecialHolidayEvent(false,
				new SpecialHolidayCode(removeSpecialHolidayCommand.getSpecialHolidayCode()),
				null);
		event.toBePublished();
	}

}
