package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWkpCtgRepository;

import javax.inject.Inject;

public class DeleteAlarmCheckCdtWkpCommandHandler extends CommandHandler<DeleteAlarmCheckCdtWkpCommand> {

    @Inject
    private AlarmCheckCdtWkpCtgRepository alarmCheckCdtWkpCtgRepo;

    @Override
    protected void handle(CommandHandlerContext<DeleteAlarmCheckCdtWkpCommand> context) {

        DeleteAlarmCheckCdtWkpCommand command = context.getCommand();

        alarmCheckCdtWkpCtgRepo.delete(command.getCategory(), command.getCode());

    }

}
