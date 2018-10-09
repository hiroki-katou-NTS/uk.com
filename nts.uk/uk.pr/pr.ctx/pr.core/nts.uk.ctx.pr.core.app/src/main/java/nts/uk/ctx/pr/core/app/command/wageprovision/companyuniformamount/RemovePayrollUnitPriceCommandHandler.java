package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.companyuniformamount.PayrollUnitPriceRepository;


@Stateless
@Transactional
public class RemovePayrollUnitPriceCommandHandler extends CommandHandler<PayrollUnitPriceCommand>
{
    
    @Inject
    private PayrollUnitPriceRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<PayrollUnitPriceCommand> context) {
        String code = context.getCommand().getCode();
        String cid = context.getCommand().getCId();
        repository.remove(code, cid);
    }
}
