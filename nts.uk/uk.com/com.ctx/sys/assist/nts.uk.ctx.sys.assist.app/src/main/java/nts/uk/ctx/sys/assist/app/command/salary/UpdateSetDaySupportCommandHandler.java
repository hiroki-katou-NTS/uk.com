package nts.uk.ctx.sys.assist.app.command.salary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.salary.SetDaySupport;
import nts.uk.ctx.sys.assist.dom.salary.SetDaySupportRepository;

@Stateless
@Transactional
public class UpdateSetDaySupportCommandHandler extends CommandHandler<SetDaySupportCommand>
{
    
    @Inject
    private SetDaySupportRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SetDaySupportCommand> context) {
        SetDaySupportCommand updateCommand = context.getCommand();
        repository.update(new SetDaySupport(updateCommand.getCid(), updateCommand.getProcessCateNo(), updateCommand.getCloseDateTime(), updateCommand.getEmpInsurdStanDate(), updateCommand.getClosureDateAccounting(), updateCommand.getPaymentDate(), updateCommand.getEmpExtraRefeDate(), updateCommand.getSocialInsurdStanDate(), updateCommand.getSocialInsurdCollecMonth(), updateCommand.getProcessDate(), updateCommand.getIncomeTaxDate(), updateCommand.getNumberWorkDay()));
    
    }
}
