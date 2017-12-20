package nts.uk.ctx.at.shared.app.command.calculation.holiday;
/**
 * @author phongtq
 * The class Add Holiday Addtime Command Handler
 */
import java.util.List;
import javax.inject.Inject;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtime;
import nts.uk.ctx.at.shared.dom.calculation.holiday.HolidayAddtimeRepository;
import nts.uk.shr.com.context.AppContexts;

public class AddHolidayAddtimeCommandHandler extends CommandHandlerWithResult<AddHolidayAddtimeCommand, List<String>> {
	
	/** The Repository */
	@Inject
	private HolidayAddtimeRepository repository;

	@Override
	protected List<String> handle(CommandHandlerContext<AddHolidayAddtimeCommand> context) {
		AddHolidayAddtimeCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		// convert to domain
		HolidayAddtime holidayAddtime = command.toDomain(companyId);
		holidayAddtime.validate();

		// add  Holiday Addtime
		repository.add(holidayAddtime);
		return null;
	}
}
