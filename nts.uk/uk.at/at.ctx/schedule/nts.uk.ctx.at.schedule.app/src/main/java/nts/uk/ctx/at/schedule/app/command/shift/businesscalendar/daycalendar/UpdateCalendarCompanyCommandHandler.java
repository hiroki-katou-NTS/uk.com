package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompanyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateCalendarCompanyCommandHandler extends CommandHandler<List<UpdateCalendarCompanyCommand>> {
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	@Inject
	private CalendarCompanyRepository calendarCompanyRepo;

	@Override
	protected void handle(CommandHandlerContext<List<UpdateCalendarCompanyCommand>> context) {
		String companyId = AppContexts.user().companyId();
		List<UpdateCalendarCompanyCommand> calendarCompanyCommands = context.getCommand();
		for(UpdateCalendarCompanyCommand calendarCompanyCommand: calendarCompanyCommands) {
			CalendarCompany calendarCompany = CalendarCompany.createFromJavaType(companyId, 
					GeneralDate.fromString(calendarCompanyCommand.getDate(), DATE_FORMAT),
					calendarCompanyCommand.getWorkingDayAtr());
			Optional<CalendarCompany> calendarCom =calendarCompanyRepo.findCalendarCompanyByDate(companyId,
					GeneralDate.fromString(calendarCompanyCommand.getDate(), DATE_FORMAT));
			//neu da ton tai
			if (calendarCom.isPresent()) {
				//update
				calendarCompanyRepo.updateCalendarCompany(calendarCompany);
			} else {
				//insert
				calendarCompanyRepo.addCalendarCompany(calendarCompany);
			}
		}
	}

}
