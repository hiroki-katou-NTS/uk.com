package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.daycalendar;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkPlaceRepository;

@Stateless
public class DeleteCalendarWorkplaceCommandHandler extends CommandHandler<DeleteCalendarWorkplaceCommand> {

	@Inject
	private CalendarWorkPlaceRepository calendarWorkplaceRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteCalendarWorkplaceCommand> context) {
		
		calendarWorkplaceRepo.deleteCalendarWorkPlaceByYearMonth(
				context.getCommand().getWorkPlaceId(), 
				context.getCommand().getYearMonth());
		
	}
	
}
