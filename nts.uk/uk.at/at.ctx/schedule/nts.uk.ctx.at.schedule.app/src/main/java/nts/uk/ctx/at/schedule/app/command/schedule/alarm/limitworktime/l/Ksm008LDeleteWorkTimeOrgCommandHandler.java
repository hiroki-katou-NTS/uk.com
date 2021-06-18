package nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.l;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeOrganizationRepo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * KSM008L:組織の就業時間帯の期間内上限勤務を削除する
 *
 * @Author Md Rafiqul Islam
 */

@Stateless
public class Ksm008LDeleteWorkTimeOrgCommandHandler extends CommandHandler<Ksm008LDeleteWorkTimeOrgCommand> {

    @Inject
    private MaxDayOfWorkTimeOrganizationRepo maxDayOfWorkTimeOrganizationRepo;

    @Override
    protected void handle(CommandHandlerContext<Ksm008LDeleteWorkTimeOrgCommand> context) {
        Ksm008LDeleteWorkTimeOrgCommand appCommand = context.getCommand();
        TargetOrgIdenInfor targetOrgIdenInfor = appCommand.getWorkPlaceUnit() == TargetOrganizationUnit.WORKPLACE.value
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(appCommand.getWorkPlaceId())
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(appCommand.getWorkPlaceGroup());
        maxDayOfWorkTimeOrganizationRepo.delete(AppContexts.user().companyId(),
                targetOrgIdenInfor,
                new MaxDayOfWorkTimeCode(appCommand.getCode())
        );
    }
}
