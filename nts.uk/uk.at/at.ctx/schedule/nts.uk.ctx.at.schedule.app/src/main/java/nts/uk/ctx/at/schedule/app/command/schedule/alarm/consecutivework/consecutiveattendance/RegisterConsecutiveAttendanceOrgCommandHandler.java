package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.ConsecutiveNumberOfDays;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsAttOrgRepository;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsecutiveAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsecutiveAttendanceOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 組織の連続出勤できる上限日数を登録する
 */
@Stateless
public class RegisterConsecutiveAttendanceOrgCommandHandler extends CommandHandler<RegisterConsecutiveAttendanceOrgDto> {

    @Inject
    private MaxDaysOfConsAttOrgRepository maxDaysOfConsAttOrgRepo;

    @Override
    protected void handle(CommandHandlerContext<RegisterConsecutiveAttendanceOrgDto> context) {
        RegisterConsecutiveAttendanceOrgDto command = context.getCommand();

        TargetOrganizationUnit unit = EnumAdaptor.valueOf(command.getUnit(), TargetOrganizationUnit.class);
        Optional<String> workplaceId = Optional.ofNullable(command.getWorkplaceId());
        Optional<String> workplaceGroupId = Optional.ofNullable(command.getWorkplaceGroupId());
        TargetOrgIdenInfor targeOrg = new TargetOrgIdenInfor(unit, workplaceId, workplaceGroupId);

        String companyId = AppContexts.user().companyId();

        ConsecutiveNumberOfDays newDay = new ConsecutiveNumberOfDays(command.getMaxConsDays());
        MaxDaysOfConsecutiveAttendance newConsDay = new MaxDaysOfConsecutiveAttendance(newDay);
        MaxDaysOfConsecutiveAttendanceOrganization newMaxConsDays = new MaxDaysOfConsecutiveAttendanceOrganization(targeOrg, newConsDay);

        //1. get
        Optional<MaxDaysOfConsecutiveAttendanceOrganization> oldMaxConsDays = maxDaysOfConsAttOrgRepo.get(targeOrg, companyId);

        if (oldMaxConsDays.isPresent()) {
            //2. isPresent == true: set
            maxDaysOfConsAttOrgRepo.update(newMaxConsDays, companyId);
        }
        else {
            //3. isPresent == false: create
            maxDaysOfConsAttOrgRepo.insert(newMaxConsDays, companyId);
        }
    }
}
