package nts.uk.ctx.at.schedule.app.command.shift.businesscalendar.holiday;

import java.util.Optional;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHoliday;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.service.PublicHolidayService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author hieult
 *
 */
@Stateless
@Transactional
public class UpdatePublicHolidayCommandHandler extends CommandHandler<UpdatePublicHolidayCommand> {

	//@Inject
	//private PublicHolidayService publicHolidayService;

	@Inject
	private PublicHolidayRepository publicHolidayRepository;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";

	@Override
	protected void handle(CommandHandlerContext<UpdatePublicHolidayCommand> context) {
		String companyID = AppContexts.user().companyId();
		UpdatePublicHolidayCommand command = context.getCommand();

		// Check PulbicHoliday is Existence \
		Optional<PublicHoliday> checkPublicHoliday = publicHolidayRepository.getHolidaysByDate(companyID,
				GeneralDate.fromString(context.getCommand().getDate(), DATE_FORMAT));
		if (!checkPublicHoliday.isPresent()) {
			throw new BusinessException("Msg_132");
		}
		// Check input
		if (StringUtil.isNullOrEmpty(command.getDate().toString(), true)
				|| StringUtil.isNullOrEmpty(command.getHolidayName(), true))
			throw new BusinessException("");
		publicHolidayRepository.update(context.getCommand().toDomain());
	}

}
