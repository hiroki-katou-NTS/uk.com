package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.j;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.ConsecutiveNumberOfDays;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.ConsecutiveWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.ConsecutiveWorkTimeName;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfConsecutiveWorkTime;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeOrganization;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeOrganizationRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * KSM008J:組織の就業時間帯の連続勤務できる上限日数を更新する
 *
 * @Author Md Rafiqul Islam
 */

@Stateless
public class Ksm008JUpdateWorkTimeOrgCommandHandler extends CommandHandler<Ksm008JUpdateWorkTimeOrgCommand> {

    @Inject
    private MaxDaysOfContinuousWorkTimeOrganizationRepository maxDaysOfContinuousWorkTimeOrganizationRepository;

    @Override
    protected void handle(CommandHandlerContext<Ksm008JUpdateWorkTimeOrgCommand> context) {
        Ksm008JUpdateWorkTimeOrgCommand appCommand = context.getCommand();
        TargetOrgIdenInfor targetOrgIdenInfor = appCommand.getWorkPlaceUnit() == TargetOrganizationUnit.WORKPLACE.value
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(appCommand.getWorkPlaceId())
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(appCommand.getWorkPlaceGroup());
        maxDaysOfContinuousWorkTimeOrganizationRepository.update(AppContexts.user().companyId(), new MaxDaysOfContinuousWorkTimeOrganization(
                targetOrgIdenInfor,
                new ConsecutiveWorkTimeCode(appCommand.getCode()),
                new ConsecutiveWorkTimeName(appCommand.getName()),
                new MaxDaysOfConsecutiveWorkTime(
                        appCommand
                                .getWorkTimeCodes()
                                .stream()
                                .map(item -> new WorkTimeCode(item))
                                .collect(Collectors.toList()),
                        new ConsecutiveNumberOfDays(appCommand.getNumberOfDays())
                )
        ));
    }
}
