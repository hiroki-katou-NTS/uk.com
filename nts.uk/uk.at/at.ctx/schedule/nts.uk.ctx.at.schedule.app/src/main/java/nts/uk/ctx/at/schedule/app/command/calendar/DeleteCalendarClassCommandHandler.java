package nts.uk.ctx.at.schedule.app.command.calendar;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarClass;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteCalendarClassCommandHandler extends CommandHandler<DeleteCalendarClassCommand> {

	@Inject
	private CalendarRepository calendarClassRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteCalendarClassCommand> context) {
		String companyId = AppContexts.user().companyId();
		Optional<CalendarClass> calendarCla =calendarClassRepo.findCalendarClassByDate(companyId,
				context.getCommand().getClassId(),
				context.getCommand().getDateId());
		if (calendarCla.isPresent()) {
			calendarClassRepo.deleteCalendarClass(companyId,
					context.getCommand().getClassId(),
					context.getCommand().getDateId());
			
		} else {
			//do something
		}
		
	}

	
}
