package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsAttComRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 会社の連続出勤できる上限日数を削除する
 */
@Stateless
public class DeleteConsecutiveAttendanceComCommandHandler extends CommandHandler<DeleteConsecutiveAttendanceComDto> {

    @Inject
    private MaxDaysOfConsAttComRepository maxDaysOfConsAttComRepo;

    @Override
    protected void handle(CommandHandlerContext<DeleteConsecutiveAttendanceComDto> context) {
        maxDaysOfConsAttComRepo.delete(AppContexts.user().companyId());
    }
}
