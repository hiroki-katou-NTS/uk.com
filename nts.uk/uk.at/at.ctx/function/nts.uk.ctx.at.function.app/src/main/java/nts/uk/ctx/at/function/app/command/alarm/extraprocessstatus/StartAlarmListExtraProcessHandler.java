package nts.uk.ctx.at.function.app.command.alarm.extraprocessstatus;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.ExtractionState;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class StartAlarmListExtraProcessHandler extends CommandHandlerWithResult<ActiveAlarmListExtraProcessCommand, String> {

	@Inject
	private AlarmListExtraProcessStatusRepository repo;

	@Override
	protected String handle(CommandHandlerContext<ActiveAlarmListExtraProcessCommand> context) {
		String companyID = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		GeneralDateTime now1 = GeneralDateTime.now();
		AlarmListExtraProcessStatus alarmExtraProcessStatus = new AlarmListExtraProcessStatus(
				IdentifierUtil.randomUniqueId(), companyID, GeneralDate.today(), now1.hours() * 60 + now1.minutes(),
				employeeId, null, null,ExtractionState.PROCESSING);
		return repo.addAlListExtaProcess(alarmExtraProcessStatus);
	}
}
