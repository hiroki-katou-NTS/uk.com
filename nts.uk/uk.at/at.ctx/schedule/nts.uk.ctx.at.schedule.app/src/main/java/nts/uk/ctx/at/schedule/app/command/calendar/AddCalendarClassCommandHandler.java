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
public class AddCalendarClassCommandHandler extends CommandHandler<AddCalendarClassCommand> {
	
	@Inject
	private CalendarRepository calendarClassRepo;

	@Override
	protected void handle(CommandHandlerContext<AddCalendarClassCommand> context) {
		String companyId = AppContexts.user().companyId();
		CalendarClass clendarClass = CalendarClass.createFromJavaType(companyId,
				context.getCommand().getClassId(), 
				context.getCommand().getDateId(),
				context.getCommand().getWorkingDayAtr());
		Optional<CalendarClass> clendarCla =calendarClassRepo.findCalendarClassByDate(companyId,
				context.getCommand().getClassId(),
				context.getCommand().getDateId());
		if (clendarCla.isPresent()) {
			//do something
		} else {
			calendarClassRepo.addCalendarClass(clendarClass);
		}
	}

}
