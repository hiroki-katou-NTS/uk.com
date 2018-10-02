package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceSettingRepository;

import javax.transaction.Transactional;

@Stateless
@Transactional
public class RemovepayrollUnitPriceSettingCommandHandler extends CommandHandler<PayrollUnitPriceSettingCommand>
{
    
    @Inject
    private PayrollUnitPriceSettingRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<PayrollUnitPriceSettingCommand> context) {
        String hisId = context.getCommand().getHistoryId();
        repository.remove(hisId);
    }
}
