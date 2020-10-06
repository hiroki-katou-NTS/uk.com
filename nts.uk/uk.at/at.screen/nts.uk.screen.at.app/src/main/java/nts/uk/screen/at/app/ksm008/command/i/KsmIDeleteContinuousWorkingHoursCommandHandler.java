package nts.uk.screen.at.app.ksm008.command.i;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.ConsecutiveWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeCompanyRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class KsmIDeleteContinuousWorkingHoursCommandHandler extends CommandHandler<KsmIDeleteContinuousWorkingHoursCommand> {

    @Inject
    private MaxDaysOfContinuousWorkTimeCompanyRepository maxDaysOfContinuousWorkTimeCompanyRepository;

    @Override
    protected void handle(CommandHandlerContext<KsmIDeleteContinuousWorkingHoursCommand> context) {
        KsmIDeleteContinuousWorkingHoursCommand appCommand = context.getCommand();
        maxDaysOfContinuousWorkTimeCompanyRepository.delete(appCommand.getCompnayID(), new ConsecutiveWorkTimeCode(appCommand.getCode()));
    }
}
