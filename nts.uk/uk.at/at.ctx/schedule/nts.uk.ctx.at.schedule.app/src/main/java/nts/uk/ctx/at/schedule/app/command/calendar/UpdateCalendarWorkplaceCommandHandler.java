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
public class UpdateCalendarWorkplaceCommandHandler extends CommandHandler<List<UpdateCalendarWorkplaceCommand>> {

	@Inject
	private CalendarWorkPlaceRepository calendarWorkplaceRepo;

	@Override
	protected void handle(CommandHandlerContext<List<UpdateCalendarWorkplaceCommand>> context) {
		List<UpdateCalendarWorkplaceCommand> calendarWorkplaceCommands = context.getCommand();
		for(UpdateCalendarWorkplaceCommand calendarWorkplaceCommand: calendarWorkplaceCommands) {
			CalendarWorkplace calendarWorkplace = CalendarWorkplace.createFromJavaType(
					calendarWorkplaceCommand.getWorkPlaceId(), 
					calendarWorkplaceCommand.getDateId(),
					calendarWorkplaceCommand.getWorkingDayAtr());
			Optional<CalendarWorkplace> calendarCom =calendarWorkplaceRepo.findCalendarWorkplaceByDate(
					calendarWorkplaceCommand.getWorkPlaceId(),
					calendarWorkplaceCommand.getDateId());
			if (calendarCom.isPresent()) {
				calendarWorkplaceRepo.updateCalendarWorkplace(calendarWorkplace);
				
			} else {
				calendarWorkplaceRepo.addCalendarWorkplace(calendarWorkplace);
			}
		}
	}
}
