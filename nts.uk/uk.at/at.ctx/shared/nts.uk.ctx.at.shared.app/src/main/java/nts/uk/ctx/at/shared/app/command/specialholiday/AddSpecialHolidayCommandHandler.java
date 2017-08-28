package nts.uk.ctx.at.shared.app.command.specialholiday;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddSpecialHolidayCommandHandler extends CommandHandler<AddSpecialHolidayCommand> {

	@Inject
	private SpecialHolidayRepository specialHolidayRepository;

	@Override
	protected void handle(CommandHandlerContext<AddSpecialHolidayCommand> context) {

		AddSpecialHolidayCommand addSpecialHolidayCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		SpecialHoliday specialHoliday = addSpecialHolidayCommand.toDomain(companyId);
		specialHoliday.validate();
		// add Special Holiday
		specialHolidayRepository.add(specialHoliday);
	}
}
