package nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.k;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCompanyRepo;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * KSM008K:会社の就業時間帯の期間内上限勤務を削除する
 *
 * @Author Md Rafiqul Islam
 */

@Stateless
public class Ksm008KDeleteCommandHandler extends CommandHandler<Ksm008KDeleteCommand> {

    @Inject
    private MaxDayOfWorkTimeCompanyRepo maxDayOfWorkTimeCompanyRepo;

    @Override
    protected void handle(CommandHandlerContext<Ksm008KDeleteCommand> context) {
        Ksm008KDeleteCommand appCommand = context.getCommand();
        maxDayOfWorkTimeCompanyRepo.delete(AppContexts.user().companyId(), new MaxDayOfWorkTimeCode(appCommand.getCode()));
    }
}
