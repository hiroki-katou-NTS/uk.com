package nts.uk.ctx.pr.shared.app.command.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfoRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class RegisterEmpInsLossInfoCommandHandler extends CommandHandler<EmpInsLossInfoCommand> {
    @Inject
    private EmpInsLossInfoRepository repository;

    @Override
    protected void handle(CommandHandlerContext<EmpInsLossInfoCommand> context){
        EmpInsLossInfoCommand command = context.getCommand();
        if (command.getScreenMode() == 0){
            repository.add(new EmpInsLossInfo(
                    AppContexts.user().companyId(),
                    command.getSId(),
                    command.getCauseOfLossAtr(),
                    command.getRequestForIssuance(),
                    command.getScheduleForReplenishment(),
                    command.getCauseOfLossEmpInsurance(),
                    command.getScheduleWorkingHourPerWeek()
            ));
        } else {
            repository.update(new EmpInsLossInfo(
                    AppContexts.user().companyId(),
                    command.getSId(),
                    command.getCauseOfLossAtr(),
                    command.getRequestForIssuance(),
                    command.getScheduleForReplenishment(),
                    command.getCauseOfLossEmpInsurance(),
                    command.getScheduleWorkingHourPerWeek()
            ));
        }
    }
}
