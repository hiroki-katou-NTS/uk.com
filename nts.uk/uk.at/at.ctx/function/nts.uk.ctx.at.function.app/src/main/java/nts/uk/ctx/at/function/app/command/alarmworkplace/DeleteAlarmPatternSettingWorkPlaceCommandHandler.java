package nts.uk.ctx.at.function.app.command.alarmworkplace;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.alarmworkplace.AlarmPatternSettingWorkPlaceRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * パータン設定を削除する
 */
@Stateless
public class DeleteAlarmPatternSettingWorkPlaceCommandHandler extends CommandHandler<DeleteAlarmPatternSettingWorkPlaceCommand> {
    @Inject
    private AlarmPatternSettingWorkPlaceRepository repository;

    @Override
    protected void handle(CommandHandlerContext<DeleteAlarmPatternSettingWorkPlaceCommand> context) {
        DeleteAlarmPatternSettingWorkPlaceCommand command = context.getCommand();
        repository.delete(AppContexts.user().companyId(),command.getCode());
    }
}
