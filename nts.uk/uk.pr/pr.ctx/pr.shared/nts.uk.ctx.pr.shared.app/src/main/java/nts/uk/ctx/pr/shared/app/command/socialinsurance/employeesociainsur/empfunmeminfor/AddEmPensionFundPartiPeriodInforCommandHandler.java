package nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.empfunmeminfor;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.shared.dom.socialinsurance.employeesociainsur.empfunmeminfor.EmPensionFundPartiPeriodInforRepository;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class AddEmPensionFundPartiPeriodInforCommandHandler extends CommandHandler<EmPensionFundPartiPeriodInforCommand>
{
    
    @Inject
    private EmPensionFundPartiPeriodInforRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<EmPensionFundPartiPeriodInforCommand> context) {

    }
}
