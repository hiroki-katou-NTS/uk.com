package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupport;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SetDaySupportRepository;

@Stateless
@Transactional
public class UpdateSetDaySupportCommandHandler extends CommandHandler<SetDaySupportCommand>
{
    
    @Inject
    private SetDaySupportRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SetDaySupportCommand> context) {
        SetDaySupportCommand updateCommand = context.getCommand();
        repository.update(new SetDaySupport(updateCommand.getCid(), updateCommand.getProcessCateNo(),updateCommand.getProcessDate(), updateCommand.getCloseDateTime(), updateCommand.getEmpInsurdStanDate(), updateCommand.getClosureDateAccounting(), updateCommand.getPaymentDate(), updateCommand.getEmpExtraRefeDate(), updateCommand.getSocialInsurdStanDate(), updateCommand.getSocialInsurdCollecMonth(),  updateCommand.getIncomeTaxDate(), updateCommand.getNumberWorkDay()));
    
    }
}
