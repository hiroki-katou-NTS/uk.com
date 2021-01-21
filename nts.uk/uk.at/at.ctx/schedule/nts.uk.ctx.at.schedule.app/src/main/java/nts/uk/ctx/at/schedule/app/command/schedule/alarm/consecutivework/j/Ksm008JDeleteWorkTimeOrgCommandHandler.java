package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.j;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.ConsecutiveWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeOrganizationRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * KSM008J:組織の就業時間帯の連続勤務できる上限日数を削除する
 *
 * @Author Md Rafiqul Islam
 */

@Stateless
public class Ksm008JDeleteWorkTimeOrgCommandHandler extends CommandHandler<Ksm008JDeleteWorkTimeOrgCommand> {

    @Inject
    private MaxDaysOfContinuousWorkTimeOrganizationRepository maxDaysOfContinuousWorkTimeOrganizationRepository;

    @Override
    protected void handle(CommandHandlerContext<Ksm008JDeleteWorkTimeOrgCommand> context) {
        Ksm008JDeleteWorkTimeOrgCommand appCommand = context.getCommand();
        TargetOrgIdenInfor targetOrgIdenInfor = appCommand.getWorkPlaceUnit() == 0
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(appCommand.getWorkPlaceId())
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(appCommand.getWorkPlaceGroup());
        maxDaysOfContinuousWorkTimeOrganizationRepository.delete(AppContexts.user().companyId(),
                targetOrgIdenInfor,
                new ConsecutiveWorkTimeCode(appCommand.getCode())
        );
    }
}
