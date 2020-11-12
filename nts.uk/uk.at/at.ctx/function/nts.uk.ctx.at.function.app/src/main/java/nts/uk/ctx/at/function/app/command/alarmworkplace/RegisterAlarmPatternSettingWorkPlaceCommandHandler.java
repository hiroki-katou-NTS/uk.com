package nts.uk.ctx.at.function.app.command.alarmworkplace;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * パターン設定を登録する
 */
//@Stateless
//public class RegisterAlarmPatternSettingWorkPlaceCommandHandler extends CommandHandler<RegisterAlarmPatternSettingWorkPlaceDto> {
//
//    @Inject
//    private MaxDaysOfConsAttOrgRepository maxDaysOfConsAttOrgRepo;
//
//    @Override
//    protected void handle(CommandHandlerContext<RegisterAlarmPatternSettingWorkPlaceDto> context) {
//        RegisterConsecutiveAttendanceOrgDto command = context.getCommand();
//
//        TargetOrganizationUnit unit = EnumAdaptor.valueOf(command.getUnit(), TargetOrganizationUnit.class);
//        Optional<String> workplaceId = Optional.ofNullable(command.getWorkplaceId());
//        Optional<String> workplaceGroupId = Optional.ofNullable(command.getWorkplaceGroupId());
//        TargetOrgIdenInfor targeOrg = new TargetOrgIdenInfor(unit, workplaceId, workplaceGroupId);
//
//        String companyId = AppContexts.user().companyId();
//
//        ConsecutiveNumberOfDays newDay = new ConsecutiveNumberOfDays(command.getMaxConsDays());
//        MaxDaysOfConsecutiveAttendance newConsDay = new MaxDaysOfConsecutiveAttendance(newDay);
//        MaxDaysOfConsecutiveAttendanceOrganization newMaxConsDays = new MaxDaysOfConsecutiveAttendanceOrganization(targeOrg, newConsDay);
//
//        //1. get
//        boolean isExists = maxDaysOfConsAttOrgRepo.exists(targeOrg, companyId);
//
//        if (isExists) {
//            //2. isPresent == true: set
//            maxDaysOfConsAttOrgRepo.update(newMaxConsDays, companyId);
//        }
//        else {
//            //3. isPresent == false: create
//            maxDaysOfConsAttOrgRepo.insert(newMaxConsDays, companyId);
//        }
//    }
//}
