package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.SalaryInsuColMonRepository;

@Stateless
@Transactional
public class RemoveSalaryInsuColMonCommandHandler extends CommandHandler<SalaryInsuColMonCommand>
{
    
    @Inject
    private SalaryInsuColMonRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalaryInsuColMonCommand> context) {
        int processCateNo = context.getCommand().getProcessCateNo();
        String cid = context.getCommand().getCid();
        repository.remove(processCateNo, cid);
    }
}
