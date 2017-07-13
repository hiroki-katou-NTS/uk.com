package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.holiday;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
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

	@Override
	protected void handle(CommandHandlerContext<DeletePublicHolidayCommand> context) {
		String companyID = AppContexts.user().companyId();
		publicHolidayService.deleteHolidayInfo(companyID, context.getCommand().getDate());
	}
}
