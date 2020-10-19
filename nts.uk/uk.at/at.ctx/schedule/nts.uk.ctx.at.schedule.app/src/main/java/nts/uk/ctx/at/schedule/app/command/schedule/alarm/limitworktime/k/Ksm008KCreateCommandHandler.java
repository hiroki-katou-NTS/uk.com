package nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.k;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.*;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * KSM008K:会社の就業時間帯の期間内上限勤務を新規する
 *
 * @Author Md Rafiqul Islam
 */

@Stateless
public class Ksm008KCreateCommandHandler extends CommandHandler<Ksm008KCreateCommand> {

    @Inject
    private MaxDayOfWorkTimeCompanyRepo maxDayOfWorkTimeCompanyRepo;

    @Override
    protected void handle(CommandHandlerContext<Ksm008KCreateCommand> context) {
        Ksm008KCreateCommand appCommand = context.getCommand();
        /*存在するか==true?*/
        if (maxDayOfWorkTimeCompanyRepo.exists(AppContexts.user().companyId(), new MaxDayOfWorkTimeCode(appCommand.getCode()))) {
            throw new BusinessException("Msg_3");
        } else {
            maxDayOfWorkTimeCompanyRepo.insert(AppContexts.user().companyId(), new MaxDayOfWorkTimeCompany(
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
}
