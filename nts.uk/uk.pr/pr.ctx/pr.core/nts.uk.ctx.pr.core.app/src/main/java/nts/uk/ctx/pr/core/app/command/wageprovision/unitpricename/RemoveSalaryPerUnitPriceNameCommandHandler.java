package nts.uk.ctx.pr.core.app.command.wageprovision.unitpricename;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceNameRepository;

@Stateless
@Transactional
public class RemoveSalaryPerUnitPriceNameCommandHandler extends CommandHandler<SalaryPerUnitPriceNameCommand>
{
    
    @Inject
    private SalaryPerUnitPriceNameRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalaryPerUnitPriceNameCommand> context) {
        String cid = context.getCommand().getCid();
        String code = context.getCommand().getCode();
        repository.remove(cid, code);
    }
}
