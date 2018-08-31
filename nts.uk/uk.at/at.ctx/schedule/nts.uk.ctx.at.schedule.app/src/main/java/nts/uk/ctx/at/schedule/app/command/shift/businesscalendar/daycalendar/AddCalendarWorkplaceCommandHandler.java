package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkPlaceRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;

@Stateless
public class AddCalendarWorkplaceCommandHandler extends CommandHandler<List<AddCalendarWorkplaceCommand>> {
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	@Inject
	private CalendarWorkPlaceRepository calendarWorkplaceRepo;

	@Override
	protected void handle(CommandHandlerContext<List<AddCalendarWorkplaceCommand>> context) {
		List<AddCalendarWorkplaceCommand> calendarWorkplaceCommands = context.getCommand();
		for(AddCalendarWorkplaceCommand calendarWorkplaceCommand: calendarWorkplaceCommands) {
			CalendarWorkplace calendarCompany = CalendarWorkplace.createFromJavaType(
					calendarWorkplaceCommand.getWorkPlaceId(), 
					GeneralDate.fromString(calendarWorkplaceCommand.getDate(), DATE_FORMAT),
					calendarWorkplaceCommand.getWorkingDayAtr());
			Optional<CalendarWorkplace> calendarCom = calendarWorkplaceRepo.findCalendarWorkplaceByDate(
					calendarWorkplaceCommand.getWorkPlaceId(),
					GeneralDate.fromString(calendarWorkplaceCommand.getDate(), DATE_FORMAT));
			if (calendarCom.isPresent()) {
				//do something
			} else {
				calendarWorkplaceRepo.addCalendarWorkplace(calendarCompany);
			}
		}
	}
}
