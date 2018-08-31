package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClass;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClassRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class UpdateCalendarClassCommandHandler extends CommandHandler<List<UpdateCalendarClassCommand>> {
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	@Inject
	private CalendarClassRepository calendarClassRepo;

	@Override
	protected void handle(CommandHandlerContext<List<UpdateCalendarClassCommand>> context) {
		String companyId = AppContexts.user().companyId();
		List<UpdateCalendarClassCommand> calendarClassCommands = context.getCommand();
		for(UpdateCalendarClassCommand calendarClassCommand: calendarClassCommands) {
			CalendarClass calendarClass = CalendarClass.createFromJavaType(companyId,
					calendarClassCommand.getClassId(), 
					GeneralDate.fromString(calendarClassCommand.getDate(), DATE_FORMAT),
					calendarClassCommand.getWorkingDayAtr());
			Optional<CalendarClass> calendarCla =calendarClassRepo.findCalendarClassByDate(companyId,
					calendarClassCommand.getClassId(),
					GeneralDate.fromString(calendarClassCommand.getDate(), DATE_FORMAT));
			// neu da ton tai
			if (calendarCla.isPresent()) {
				calendarClassRepo.updateCalendarClass(calendarClass);
				
			} else {
				calendarClassRepo.addCalendarClass(calendarClass);
			}
		}
	}
	
}
