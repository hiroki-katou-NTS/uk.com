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
public class UpdateCalendarClassCommandHandler extends CommandHandler<UpdateCalendarClassCommand> {

	@Inject
	private CalendarRepository calendarClassRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateCalendarClassCommand> context) {
		String companyId = AppContexts.user().companyId();
		CalendarClass calendarClass = CalendarClass.createFromJavaType(companyId,
				context.getCommand().getClassId(), 
				context.getCommand().getDateId(),
				context.getCommand().getWorkingDayAtr());
		Optional<CalendarClass> calendarCla =calendarClassRepo.findCalendarClassByDate(companyId,
				context.getCommand().getClassId(),
				context.getCommand().getDateId());
		if (calendarCla.isPresent()) {
			calendarClassRepo.updateCalendarClass(calendarClass);
			
		} else {
			//do something
		}
		
	}
	
}
