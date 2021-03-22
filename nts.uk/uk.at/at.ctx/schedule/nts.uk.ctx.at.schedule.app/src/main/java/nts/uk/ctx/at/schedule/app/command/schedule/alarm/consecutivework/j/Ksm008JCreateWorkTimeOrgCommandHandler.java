package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.j;

import nts.arc.error.BusinessException;
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
 * KSM008J:組織の就業時間帯の連続勤務できる上限日数を新規する
 *
 * @Author Md Rafiqul Islam
 */

@Stateless
public class Ksm008JCreateWorkTimeOrgCommandHandler extends CommandHandler<Ksm008JCreateWorkTimeOrgCommand> {

    @Inject
    private MaxDaysOfContinuousWorkTimeOrganizationRepository maxDaysOfContinuousWorkTimeOrganizationRepository;

    @Override
    protected void handle(CommandHandlerContext<Ksm008JCreateWorkTimeOrgCommand> context) {
        Ksm008JCreateWorkTimeOrgCommand appCommand = context.getCommand();
        TargetOrgIdenInfor targetOrgIdenInfor = appCommand.getWorkPlaceUnit() == TargetOrganizationUnit.WORKPLACE.value
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(appCommand.getWorkPlaceId())
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(appCommand.getWorkPlaceGroup());
        boolean isExist = maxDaysOfContinuousWorkTimeOrganizationRepository.exists(AppContexts.user().companyId(),
                targetOrgIdenInfor,
                new ConsecutiveWorkTimeCode(appCommand.getCode()));
        if (isExist) {
            throw new BusinessException("Msg_3");
        } else {
            maxDaysOfContinuousWorkTimeOrganizationRepository.insert(AppContexts.user().companyId(), new MaxDaysOfContinuousWorkTimeOrganization(
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
}
