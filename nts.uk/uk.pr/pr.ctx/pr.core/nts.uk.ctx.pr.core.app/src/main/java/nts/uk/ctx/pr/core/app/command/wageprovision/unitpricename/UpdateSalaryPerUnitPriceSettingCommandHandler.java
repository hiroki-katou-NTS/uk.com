package nts.uk.ctx.pr.core.app.command.wageprovision.unitpricename;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceSettingRepository;

@Stateless
@Transactional
public class UpdateSalaryPerUnitPriceSettingCommandHandler extends CommandHandler<SalaryPerUnitPriceSettingCommand>
{
    
    @Inject
    private SalaryPerUnitPriceSettingRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalaryPerUnitPriceSettingCommand> context) {
        SalaryPerUnitPriceSettingCommand command = context.getCommand();
        repository.update(new SalaryPerUnitPriceSetting(command.getCid(), command.getCode(), command.getUnitPriceType(), command.getSettingAtr(), command.getTargetClassification(), command.getMonthlySalary(), command.getMonthlySalaryPerday(), command.getDayPayee(), command.getHourlyPay()));
    
    }
}
