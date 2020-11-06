package nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.worktogether;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.together.WorkTogetherRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM008 スケジュールのアラームチェック設定.B: 同時出勤指定.メニュー別OCD.同時出勤指定を削除する
 */

@Stateless
public class DeleteWorkTogetherCommandHandler extends CommandHandler<DeleteWorkTogetherCommand> {

    @Inject
    private WorkTogetherRepository workTogetherRepository;

    @Override
    protected void handle(CommandHandlerContext<DeleteWorkTogetherCommand> context) {
        DeleteWorkTogetherCommand command = context.getCommand();
        workTogetherRepository.delete(command.getSid());
    }
}
