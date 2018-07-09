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
public class AddCalendarClassCommandHandler extends CommandHandler<List<AddCalendarClassCommand>> {
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	@Inject
	private CalendarClassRepository calendarClassRepo;

	@Override
	protected void handle(CommandHandlerContext<List<AddCalendarClassCommand>> context) {
		String companyId = AppContexts.user().companyId();
		List<AddCalendarClassCommand> calendarClassCommands = context.getCommand();
		for(AddCalendarClassCommand calendarClassCommand : calendarClassCommands){
			CalendarClass clendarClass = CalendarClass.createFromJavaType(companyId,
					calendarClassCommand.getClassId(), 
					GeneralDate.fromString(calendarClassCommand.getDate(), DATE_FORMAT),
					calendarClassCommand.getWorkingDayAtr());
			Optional<CalendarClass> clendarCla =calendarClassRepo.findCalendarClassByDate(companyId,
					calendarClassCommand.getClassId(),
					GeneralDate.fromString(calendarClassCommand.getDate(), DATE_FORMAT));
			if (clendarCla.isPresent()) {
				//do something
			} else {
				calendarClassRepo.addCalendarClass(clendarClass);
			}
		}
	}

}
