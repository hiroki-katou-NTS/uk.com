package nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.k;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDay;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTime;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCompany;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCompanyRepo;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeName;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * KSM008K:会社の就業時間帯の期間内上限勤務を更新する
 *
 * @Author Md Rafiqul Islam
 */

@Stateless
public class Ksm008KUpdateCommandHandler extends CommandHandler<Ksm008KUpdateCommand> {

    @Inject
    private MaxDayOfWorkTimeCompanyRepo maxDayOfWorkTimeCompanyRepo;

    @Override
    protected void handle(CommandHandlerContext<Ksm008KUpdateCommand> context) {
        Ksm008KUpdateCommand appCommand = context.getCommand();
        maxDayOfWorkTimeCompanyRepo.update(AppContexts.user().companyId(), new MaxDayOfWorkTimeCompany(
                new MaxDayOfWorkTimeCode(appCommand.getCode()),
                new MaxDayOfWorkTimeName(appCommand.getName()),
                new MaxDayOfWorkTime(
                        appCommand
                                .getWorkTimeCodes()
                                .stream()
                                .map(item -> new WorkTimeCode(item))
                                .collect(Collectors.toList()),
                        new MaxDay(appCommand.getMaxDay()))
        ));
    }
}
