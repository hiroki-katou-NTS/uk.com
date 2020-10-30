package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.WkpTimeZonePeopleNumber;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople.WorkplaceCounterStartTime;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople.WorkplaceCounterTimeZonePeopleNumber;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople.WorkplaceCounterTimeZonePeopleNumberRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 時間帯人数情報を登録する
 */
@Transactional
@Stateless
public class RegisterWkpTimeZonePeopleNumberCommandHandler extends CommandHandler<RegisterWkpTimeZonePeopleNumberCommand> {

	@Inject
	private WorkplaceCounterTimeZonePeopleNumberRepo repository;

	@Override
	protected void handle(CommandHandlerContext<RegisterWkpTimeZonePeopleNumberCommand> context) {
		RegisterWkpTimeZonePeopleNumberCommand command = context.getCommand();

		Optional<WorkplaceCounterTimeZonePeopleNumber> timeZonePeopleNumber = repository.get(AppContexts.user().companyId());
		WorkplaceCounterTimeZonePeopleNumber newTimeZonePeopleNumber =
			new WorkplaceCounterTimeZonePeopleNumber(command.getTimeZone().stream().map(WorkplaceCounterStartTime::new).collect(Collectors.toList()));
		if (timeZonePeopleNumber.isPresent()){
			repository.update(AppContexts.user().companyId(), newTimeZonePeopleNumber);
		}else {
			repository.insert(AppContexts.user().companyId(), newTimeZonePeopleNumber);
		}
	}

}
