package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceHistoryRepository;


@Stateless
@Transactional
public class RemovePayrollUnitPriceHistoryCommandHandler extends CommandHandler<PayrollUnitPriceHistoryCommand>
{
    
    @Inject
    private PayrollUnitPriceHistoryRepository repository;

    @Override
    protected void handle(CommandHandlerContext<PayrollUnitPriceHistoryCommand> context) {
        String cid = context.getCommand().getCId();
        String code = context.getCommand().getCode();
        String hisId = context.getCommand().getHisId();
        repository.remove(cid, code, hisId);
    }
}
