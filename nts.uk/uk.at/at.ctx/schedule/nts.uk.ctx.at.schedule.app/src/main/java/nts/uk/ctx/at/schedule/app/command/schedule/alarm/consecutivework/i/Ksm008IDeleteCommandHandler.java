package nts.uk.ctx.at.schedule.app.command.schedule.alarm.consecutivework.i;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.ConsecutiveWorkTimeCode;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime.MaxDaysOfContinuousWorkTimeCompanyRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class Ksm008IDeleteCommandHandler extends CommandHandler<Ksm008IDeleteCommand> {

    @Inject
    private MaxDaysOfContinuousWorkTimeCompanyRepository maxDaysOfContinuousWorkTimeCompanyRepository;

    @Override
    protected void handle(CommandHandlerContext<Ksm008IDeleteCommand> context) {
        Ksm008IDeleteCommand appCommand = context.getCommand();
        maxDaysOfContinuousWorkTimeCompanyRepository.delete(AppContexts.user().companyId(), new ConsecutiveWorkTimeCode(appCommand.getCode()));
    }
}
