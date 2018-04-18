package nts.uk.ctx.at.function.app.command.alarm.extraprocessstatus;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
@Stateless
public class DeleteAlarmListExtraProcessStatusCmdHandler extends CommandHandler<DeleteAlarmListExtraProcessStatusCmd> {

	@Inject
	private  AlarmListExtraProcessStatusRepository repo;

	@Override
	protected void handle(CommandHandlerContext<DeleteAlarmListExtraProcessStatusCmd> context) {
		DeleteAlarmListExtraProcessStatusCmd command = context.getCommand();
		Optional<AlarmListExtraProcessStatus> data = this.repo.getAlListExtaProcessByID(command.getExtraProcessStatusID());
		if(data.isPresent()) {
			this.repo.deleteAlListExtaProcess(command.getExtraProcessStatusID());
		}
	}
	
}
