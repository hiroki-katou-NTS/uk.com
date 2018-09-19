package nts.uk.ctx.at.function.app.command.alarm.extraprocessstatus;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.ExtractionState;

@Stateless
public class FinishAlarmListExtraProcessHandler extends CommandHandler<ActiveAlarmListExtraProcessCommand> {

	@Inject
	private AlarmListExtraProcessStatusRepository repo;

	@Override
	protected void handle(CommandHandlerContext<ActiveAlarmListExtraProcessCommand> context) {
		this.repo.getAlListExtaProcessByID(context.getCommand().getProcessStatusId()).ifPresent(status -> {
			GeneralDateTime now = GeneralDateTime.now();
			ExtractionState extraStatus = EnumAdaptor.valueOf(context.getCommand().getStatus(), ExtractionState.class);
			status.setEndDateAndEndTime(GeneralDate.today(), now.hours() * 60 + now.minutes(),extraStatus);
			repo.updateAlListExtaProcess(status);
		});
	}
}
