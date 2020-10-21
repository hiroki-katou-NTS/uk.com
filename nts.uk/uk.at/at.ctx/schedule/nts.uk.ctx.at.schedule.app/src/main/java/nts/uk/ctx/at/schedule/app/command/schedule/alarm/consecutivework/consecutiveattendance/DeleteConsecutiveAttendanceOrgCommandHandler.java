package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsAttOrgRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 組織の連続出勤できる上限日数を削除する
 */
@Stateless
public class DeleteConsecutiveAttendanceOrgCommandHandler extends CommandHandler<DeleteConsecutiveAttendanceOrgDto> {
    @Inject
    private MaxDaysOfConsAttOrgRepository maxDaysOfConsAttOrgRepo;

    @Override
    protected void handle(CommandHandlerContext<DeleteConsecutiveAttendanceOrgDto> context) {
        DeleteConsecutiveAttendanceOrgDto command = context.getCommand();

        TargetOrganizationUnit unit = EnumAdaptor.valueOf(command.getUnit(), TargetOrganizationUnit.class);
        Optional<String> workplaceId = Optional.of(command.getWorkplaceId());
        Optional<String> workplaceGroupId = Optional.of(command.getWorkplaceGroupId());
        TargetOrgIdenInfor targeOrg = new TargetOrgIdenInfor(unit, workplaceId, workplaceGroupId);

        maxDaysOfConsAttOrgRepo.delete(targeOrg, AppContexts.user().companyId());
    }
}
