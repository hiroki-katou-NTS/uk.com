package nts.uk.ctx.at.schedule.app.command.calendar;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarWorkPlaceRepository;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarWorkplace;

@Stateless
public class AddCalendarWorkplaceCommandHandler extends CommandHandler<List<AddCalendarWorkplaceCommand>> {

	@Inject
	private CalendarWorkPlaceRepository calendarWorkplaceRepo;

	@Override
	protected void handle(CommandHandlerContext<List<AddCalendarWorkplaceCommand>> context) {
		String workPlaceId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d").toString();
		List<AddCalendarWorkplaceCommand> calendarWorkplaceCommands = context.getCommand();
		for(AddCalendarWorkplaceCommand calendarWorkplaceCommand: calendarWorkplaceCommands) {
			CalendarWorkplace calendarCompany = CalendarWorkplace.createFromJavaType(workPlaceId, 
					calendarWorkplaceCommand.getDateId(),
					calendarWorkplaceCommand.getWorkingDayAtr());
			Optional<CalendarWorkplace> calendarCom =calendarWorkplaceRepo.findCalendarWorkplaceByDate(workPlaceId,
					calendarWorkplaceCommand.getDateId());
			if (calendarCom.isPresent()) {
				//do something
			} else {
				calendarWorkplaceRepo.addCalendarWorkplace(calendarCompany);
			}
		}
	}
}
