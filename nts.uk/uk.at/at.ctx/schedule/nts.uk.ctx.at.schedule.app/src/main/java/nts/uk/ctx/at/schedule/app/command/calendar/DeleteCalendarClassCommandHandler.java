package nts.uk.ctx.at.schedule.app.command.calendar;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.calendar.CalendarClassRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DeleteCalendarClassCommandHandler extends CommandHandler<DeleteCalendarClassCommand> {

	@Inject
	private CalendarClassRepository calendarClassRepo;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteCalendarClassCommand> context) {
		String companyId = AppContexts.user().companyId();
		
		calendarClassRepo.deleteCalendarClassByYearMonth(
				companyId, 
				context.getCommand().getClassId(), 
				context.getCommand().getYearMonth());
		
	}

	
}
