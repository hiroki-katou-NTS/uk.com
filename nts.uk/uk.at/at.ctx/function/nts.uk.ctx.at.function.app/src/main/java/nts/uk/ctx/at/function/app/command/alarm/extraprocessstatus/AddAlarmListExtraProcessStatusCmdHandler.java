package nts.uk.ctx.at.function.app.command.alarm.extraprocessstatus;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;

@Stateless
public class AddAlarmListExtraProcessStatusCmdHandler extends CommandHandler<AlarmListExtraProcessStatusCmd> {

	@Inject
	private  AlarmListExtraProcessStatusRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<AlarmListExtraProcessStatusCmd> context) {
		AlarmListExtraProcessStatusCmd command = context.getCommand();
		Optional<AlarmListExtraProcessStatus> data = this.repo.getAlListExtaProcess(command.getCompanyID(), command.getStartDate(), command.getStartTime());
		if(data.isPresent()) {
			throw new BusinessException("Msg_3");
		}else {
			this.repo.addAlListExtaProcess(AlarmListExtraProcessStatusCmd.toDomain(command));
		}
	}

}
