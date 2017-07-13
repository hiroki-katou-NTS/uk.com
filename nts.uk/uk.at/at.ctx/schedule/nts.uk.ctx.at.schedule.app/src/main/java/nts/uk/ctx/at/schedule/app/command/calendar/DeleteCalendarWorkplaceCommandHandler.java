package nts.uk.ctx.at.schedule.app.command.calendar;

import java.util.Optional;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarRepository;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarWorkplace;

@Stateless
public class DeleteCalendarWorkplaceCommandHandler extends CommandHandler<DeleteCalendarWorkplaceCommand> {

	@Inject
	private CalendarRepository calendarWorkplaceRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteCalendarWorkplaceCommand> context) {
		String workPlaceId = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d").toString();
		
		Optional<CalendarWorkplace> calendarCom =calendarWorkplaceRepo.findCalendarWorkplaceByDate(workPlaceId,
				context.getCommand().getDateId());
		if (calendarCom.isPresent()) {
			calendarWorkplaceRepo.deleteCalendarWorkplace(workPlaceId,
					context.getCommand().getDateId());
			
		} else {
			//do something
		}
		
	}
	
}
