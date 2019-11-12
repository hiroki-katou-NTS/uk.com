package nts.uk.ctx.pr.shared.app.command.empinsqualifiinfo.employmentinsqualifiinfo;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsGetInfoRepository;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class UpdateEmpInsGetInfoCommandHandler extends CommandHandler<EmpInsGetInfoCommand> {
    @Inject
    private EmpInsGetInfoRepository repository;

    @Override
    protected void handle(CommandHandlerContext<EmpInsGetInfoCommand> context){
        EmpInsGetInfoCommand command = context.getCommand();
        repository.update(new EmpInsGetInfo(
                AppContexts.user().companyId(),
                command.getWorkingTime(),
                command.getAcquiAtr(),
                command.getContrPeriPrintAtr(),
                command.getJobPath(),
                command.getPayWage(),
                command.getJobAtr(),
                command.getInsCauseAtr(),
                command.getWagePaymentMode(),
                command.getEmploymentStatus()
        ));
    }
}
