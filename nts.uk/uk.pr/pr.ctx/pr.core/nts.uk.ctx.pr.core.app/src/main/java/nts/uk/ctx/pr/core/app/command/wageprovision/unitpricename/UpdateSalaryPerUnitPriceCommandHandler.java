package nts.uk.ctx.pr.core.app.command.wageprovision.unitpricename;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPrice;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceName;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceSetting;

@Stateless
@Transactional
public class UpdateSalaryPerUnitPriceCommandHandler extends CommandHandler<SalaryPerUnitPriceDataCommand>
{
    
    @Inject
    private SalaryPerUnitPriceRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalaryPerUnitPriceDataCommand> context) {
        SalaryPerUnitPriceDataCommand command = context.getCommand();

        SalaryPerUnitPriceNameCommand name = command.getSalaryPerUnitPriceName();
        SalaryPerUnitPriceSettingCommand setting = command.getSalaryPerUnitPriceSetting();

        SalaryPerUnitPriceName salaryPerUnitPriceName = new SalaryPerUnitPriceName(name.getCid(), name.getCode(), name.getName(), name.getAbolition(), name.getShortName(), name.getIntegrationCode(), name.getNote());
        SalaryPerUnitPriceSetting salaryPerUnitPriceSetting = new SalaryPerUnitPriceSetting(setting.getCid(), setting.getCode(), setting.getUnitPriceType(), setting.getSettingAtr(), setting.getTargetClassification(), setting.getMonthlySalary(), setting.getMonthlySalaryPerday(), setting.getDayPayee(), setting.getHourlyPay());

        repository.update(new SalaryPerUnitPrice(salaryPerUnitPriceName, salaryPerUnitPriceSetting));
    
    }
}
