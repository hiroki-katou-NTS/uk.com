package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.ConsecutiveNumberOfDays;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsAttOrgRepository;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsecutiveAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsecutiveAttendanceCompany;

import javax.ejb.Stateless;
import javax.inject.Inject;

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

        ConsecutiveNumberOfDays newDay = new ConsecutiveNumberOfDays(command.getMaxConsDays());
        MaxDaysOfConsecutiveAttendance newConsDay = new MaxDaysOfConsecutiveAttendance(newDay);
//        MaxDaysOfConsecutiveAttendanceCompany newMaxConsDays = new MaxDaysOfConsecutiveAttendanceCompany(newConsDay);

    }
}
