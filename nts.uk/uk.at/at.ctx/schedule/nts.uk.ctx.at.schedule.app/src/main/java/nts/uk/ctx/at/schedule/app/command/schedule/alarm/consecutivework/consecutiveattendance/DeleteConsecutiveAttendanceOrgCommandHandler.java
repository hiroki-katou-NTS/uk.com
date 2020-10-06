package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.consecutiveattendance;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsAttOrgRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class DeleteConsecutiveAttendanceOrgCommandHandler extends CommandHandler<DeleteConsecutiveAttendanceComDto> {
    @Inject
    private MaxDaysOfConsAttOrgRepository maxDaysOfConsAttOrgRepo;

    @Override
    protected void handle(CommandHandlerContext<DeleteConsecutiveAttendanceComDto> context) {
//        maxDaysOfConsAttComRepo.delete(AppContexts.user().companyId());
    }
}
