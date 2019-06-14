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
public class AddSetDaySupportCommandHandler extends CommandHandler<SetDaySupportCommand>
{
    
    @Inject
    private SetDaySupportRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SetDaySupportCommand> context) {
        SetDaySupportCommand addCommand = context.getCommand();
        repository.add(new SetDaySupport(addCommand.getCid(), addCommand.getProcessCateNo(), addCommand.getProcessDate(), addCommand.getCloseDateTime(), addCommand.getEmpInsurdStanDate(), addCommand.getClosureDateAccounting(), addCommand.getPaymentDate(), addCommand.getEmpExtraRefeDate(), addCommand.getSocialInsurdStanDate(), addCommand.getSocialInsurdCollecMonth(), addCommand.getIncomeTaxDate(), addCommand.getNumberWorkDay()));
    
    }
}
