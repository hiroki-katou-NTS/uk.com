package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.holiday;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.service.PublicHolidayService;
/**
 * 
 * @author hieult
 *
 */


@Stateless
@Transactional
public class CreatePublicHolidayCommandHandler extends CommandHandler<CreatePublicHolidayCommand> {

	@Inject

	private PublicHolidayService publicHolidayService;

	@Override
	protected void handle(CommandHandlerContext<CreatePublicHolidayCommand> context) {

		CreatePublicHolidayCommand command = context.getCommand();
			if (StringUtil.isNullOrEmpty(command.getDate().toEngineeringString(), true) || StringUtil.isNullOrEmpty(command.getHolidayName(), true)) 
				{ throw new BusinessException("");}
			publicHolidayService.createHolidayInfo(command.toDomain());
		
	}

}
