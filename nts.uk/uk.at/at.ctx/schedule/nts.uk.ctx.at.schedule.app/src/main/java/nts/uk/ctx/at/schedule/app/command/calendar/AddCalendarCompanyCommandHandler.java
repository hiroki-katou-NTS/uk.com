package nts.uk.ctx.at.schedule.app.command.calendar;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarCompany;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarCompanyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddCalendarCompanyCommandHandler extends CommandHandler<List<AddCalendarCompanyCommand>> {
	@Inject
	private CalendarCompanyRepository calendarCompanyRepo;

	@Override
	protected void handle(CommandHandlerContext<List<AddCalendarCompanyCommand>> context) {
		String companyId = AppContexts.user().companyId();
		List<AddCalendarCompanyCommand> calendarCompanyCommands = context.getCommand();
		for(AddCalendarCompanyCommand calendarCompanyCommand: calendarCompanyCommands){
			CalendarCompany calendarCompany = CalendarCompany.createFromJavaType(companyId, 
					calendarCompanyCommand.getDateId(),
					calendarCompanyCommand.getWorkingDayAtr());
			Optional<CalendarCompany> calendarCom =calendarCompanyRepo.findCalendarCompanyByDate(companyId,
					calendarCompanyCommand.getDateId());
			if (calendarCom.isPresent()) {
				//do something
			} else {
				calendarCompanyRepo.addCalendarCompany(calendarCompany);
			}
		}
	}

}
