package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompanyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteCalendarCompanyCommandHandler extends CommandHandler<DeleteCalendarCompanyCommand> {

	@Inject
	private CalendarCompanyRepository calendarCompanyRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteCalendarCompanyCommand> context) {
		String companyId = AppContexts.user().companyId();
		
		calendarCompanyRepo.deleteCalendarCompanyByYearMonth(companyId, context.getCommand().getYearMonth());
		
	}

}
