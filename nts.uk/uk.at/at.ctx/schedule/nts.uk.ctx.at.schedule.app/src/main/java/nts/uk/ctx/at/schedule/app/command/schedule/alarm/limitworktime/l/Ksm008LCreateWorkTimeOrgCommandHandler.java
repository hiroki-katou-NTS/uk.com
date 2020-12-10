package nts.uk.ctx.at.schedule.app.command.schedule.alarm.limitworktime.l;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDay;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTime;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeName;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeOrganization;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime.MaxDayOfWorkTimeOrganizationRepo;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.stream.Collectors;

/**
 * KSM008L:組織の就業時間帯の期間内上限勤務を新規する
 *
 * @Author Md Rafiqul Islam
 */

@Stateless
public class Ksm008LCreateWorkTimeOrgCommandHandler extends CommandHandler<Ksm008LCreateWorkTimeOrgCommand> {

    @Inject
    private MaxDayOfWorkTimeOrganizationRepo maxDayOfWorkTimeOrganizationRepo;

    @Override
    protected void handle(CommandHandlerContext<Ksm008LCreateWorkTimeOrgCommand> context) {
        Ksm008LCreateWorkTimeOrgCommand appCommand = context.getCommand();
        TargetOrgIdenInfor targetOrgIdenInfor = appCommand.getWorkPlaceUnit() == TargetOrganizationUnit.WORKPLACE.value
                ? TargetOrgIdenInfor.creatIdentifiWorkplace(appCommand.getWorkPlaceId())
                : TargetOrgIdenInfor.creatIdentifiWorkplaceGroup(appCommand.getWorkPlaceGroup());
        boolean isExist = maxDayOfWorkTimeOrganizationRepo.exists(AppContexts.user().companyId(),
                targetOrgIdenInfor,
                new MaxDayOfWorkTimeCode(appCommand.getCode()));
        if (isExist) {
            throw new BusinessException("Msg_3");
        } else {
            maxDayOfWorkTimeOrganizationRepo.insert(AppContexts.user().companyId(), new MaxDayOfWorkTimeOrganization(
                    targetOrgIdenInfor,
                    new MaxDayOfWorkTimeCode(appCommand.getCode()),
                    new MaxDayOfWorkTimeName(appCommand.getName()),
                    new MaxDayOfWorkTime(
                            appCommand
                                    .getWorkTimeCodes()
                                    .stream()
                                    .map(item -> new WorkTimeCode(item))
                                    .collect(Collectors.toList()),
                            new MaxDay(appCommand.getNumberOfDays())
                    )
            ));
        }
    }
}
