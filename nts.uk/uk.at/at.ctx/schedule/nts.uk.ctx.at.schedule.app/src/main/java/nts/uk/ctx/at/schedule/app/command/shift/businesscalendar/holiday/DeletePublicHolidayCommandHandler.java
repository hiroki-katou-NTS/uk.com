package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.holiday;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.service.PublicHolidayService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */

@Stateless
@Transactional
public class DeletePublicHolidayCommandHandler extends CommandHandler<DeletePublicHolidayCommand> {

	@Inject
	PublicHolidayService publicHolidayService;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	@Override
	protected void handle(CommandHandlerContext<DeletePublicHolidayCommand> context) {
		String companyID = AppContexts.user().companyId();
		publicHolidayService.deleteHolidayInfo(companyID, GeneralDate.fromString(context.getCommand().getDate(), DATE_FORMAT));
	}
}
