package nts.uk.ctx.pr.core.app.command.wageprovision.unitpricename;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceSettingRepository;
import nts.uk.shr.com.context.AppContexts;

import java.util.Optional;

@Stateless
@Transactional
public class AddSalaryPerUnitPriceSettingCommandHandler extends CommandHandler<SalaryPerUnitPriceSettingCommand>
{
    
    @Inject
    private SalaryPerUnitPriceSettingRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalaryPerUnitPriceSettingCommand> context) {
        SalaryPerUnitPriceSettingCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();

        Optional<SalaryPerUnitPriceSetting> salaryPerUnitPriceSetting = repository.getSalaryPerUnitPriceSettingById(cid, command.getCode());

        if(!salaryPerUnitPriceSetting.isPresent()) {
            repository.add(new SalaryPerUnitPriceSetting(cid, command.getCode(), command.getUnitPriceType(), command.getSettingAtr(), command.getTargetClassification(), command.getMonthlySalary(), command.getMonthlySalaryPerday(), command.getDayPayee(), command.getHourlyPay()));
        }
    }
}
