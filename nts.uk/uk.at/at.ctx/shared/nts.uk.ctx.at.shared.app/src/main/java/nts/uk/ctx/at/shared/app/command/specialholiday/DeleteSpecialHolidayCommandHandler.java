package nts.uk.ctx.at.shared.app.command.specialholiday;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
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
		
		// Delete Special Holiday
		sphdRepo.delete(companyId, command.getSpecialHolidayCode());
		
		SpecialHoliday sphd = new SpecialHoliday();
		sphd.publishEvent(false, command.getSpecialHolidayCode(), "");
	}
}
