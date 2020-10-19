package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.i;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.ConsecutiveNumberOfDays;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.*;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * KSM008I:会社の就業時間帯の連続勤務できる上限日数を更新する
 *
 * @Author Md Rafiqul Islam
 */

@Stateless
public class Ksm008IUpdateCommandHandler extends CommandHandler<Ksm008IUpdateCommand> {

    @Inject
    private MaxDaysOfContinuousWorkTimeCompanyRepository maxDaysOfContinuousWorkTimeCompanyRepository;

    @Override
    protected void handle(CommandHandlerContext<Ksm008IUpdateCommand> context) {
        Ksm008IUpdateCommand appCommand = context.getCommand();
        Optional<MaxDaysOfContinuousWorkTimeCompany> OptionalmaxDaysOfContinuousWorkTimeCompany = maxDaysOfContinuousWorkTimeCompanyRepository.get(AppContexts.user().companyId(), new ConsecutiveWorkTimeCode(appCommand.getCode()));
        if (OptionalmaxDaysOfContinuousWorkTimeCompany.isPresent()) {
            MaxDaysOfContinuousWorkTimeCompany maxDaysOfContinuousWorkTimeCompany =
                    new MaxDaysOfContinuousWorkTimeCompany(
                            new ConsecutiveWorkTimeCode(appCommand.getCode()),
                            new ConsecutiveWorkTimeName(appCommand.getName()),
                            new MaxDaysOfConsecutiveWorkTime(
                                    appCommand.getMaxDaysContiWorktime()
                                            .getWorkTimeCodes()
                                            .stream()
                                            .map(item -> new WorkTimeCode(item))
                                            .collect(Collectors.toList()),
                                    new ConsecutiveNumberOfDays(appCommand.getMaxDaysContiWorktime().getNumberOfDays())
                            )
                    );
            maxDaysOfContinuousWorkTimeCompanyRepository.update(AppContexts.user().companyId(), maxDaysOfContinuousWorkTimeCompany);
        }
    }
}
