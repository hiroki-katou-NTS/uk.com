package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.ConsecutiveNumberOfDays;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsAttComRepository;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsecutiveAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsecutiveAttendanceCompany;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Optional;

/**
 * 会社の連続出勤できる上限日数を登録する
 */
@Stateless
public class RegisterConsecutiveAttendanceComCommandHandler extends CommandHandler<RegisterConsecutiveAttendanceComDto> {

    @Inject
    private MaxDaysOfConsAttComRepository maxDaysOfConsAttComRepo;

    @Override
    protected void handle(CommandHandlerContext<RegisterConsecutiveAttendanceComDto> context) {
        RegisterConsecutiveAttendanceComDto command = context.getCommand();

        ConsecutiveNumberOfDays newDay = new ConsecutiveNumberOfDays(command.getMaxConsDays());
        MaxDaysOfConsecutiveAttendance newConsDay = new MaxDaysOfConsecutiveAttendance(newDay);
        MaxDaysOfConsecutiveAttendanceCompany newMaxConsDays = new MaxDaysOfConsecutiveAttendanceCompany(newConsDay);

        String companyId = AppContexts.user().companyId();

        //1. get
        Optional<MaxDaysOfConsecutiveAttendanceCompany> oldMaxConsDays = maxDaysOfConsAttComRepo.get(companyId);

        if (oldMaxConsDays.isPresent()) {
            //2. isPresent == true: set
            maxDaysOfConsAttComRepo.update(companyId, newMaxConsDays);
        }
        else {
            //3. isPresent == false: create
            maxDaysOfConsAttComRepo.insert(companyId, newMaxConsDays);
        }
    }
}
