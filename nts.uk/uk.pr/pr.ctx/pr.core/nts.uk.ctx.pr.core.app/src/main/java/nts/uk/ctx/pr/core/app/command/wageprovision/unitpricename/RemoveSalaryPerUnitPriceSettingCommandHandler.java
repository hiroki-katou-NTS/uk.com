package nts.uk.ctx.pr.core.app.command.wageprovision.unitpricename;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceSettingRepository;

@Stateless
@Transactional
public class RemoveSalaryPerUnitPriceSettingCommandHandler extends CommandHandler<SalaryPerUnitPriceSettingCommand>
{
    
    @Inject
    private SalaryPerUnitPriceSettingRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalaryPerUnitPriceSettingCommand> context) {
        String cid = context.getCommand().getCid();
        String code = context.getCommand().getCode();
        repository.remove(cid, code);
    }
}
