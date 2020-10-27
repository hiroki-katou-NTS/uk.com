package nts.uk.ctx.at.schedule.app.command.schedule.alarm.checksetting.worktogether;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.together.WorkTogetherRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * UKDesign.UniversalK.就業.KSM_スケジュールマスタ.KSM008 スケジュールのアラームチェック設定.B: 同時出勤指定.メニュー別OCD.同時出勤指定を新規する
 */
@Stateless
public class AddWorkTogetherCommandHandler extends CommandHandler<AddWorkTogetherCommand> {

    @Inject
    private WorkTogetherRepository workTogetherRepo;

    @Override
    protected void handle(CommandHandlerContext<AddWorkTogetherCommand> context) {
        AddWorkTogetherCommand command = context.getCommand();
        this.workTogetherRepo.insert(command.toDomain());
    }
}
