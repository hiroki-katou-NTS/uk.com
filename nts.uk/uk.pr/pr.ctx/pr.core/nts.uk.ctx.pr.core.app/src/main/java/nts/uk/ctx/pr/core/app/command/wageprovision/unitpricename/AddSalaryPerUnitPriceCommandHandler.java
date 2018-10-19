package nts.uk.ctx.pr.core.app.command.wageprovision.unitpricename;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPrice;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceName;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceSetting;
import nts.uk.shr.com.context.AppContexts;

import java.util.Optional;

@Stateless
@Transactional
public class AddSalaryPerUnitPriceCommandHandler extends CommandHandler<SalaryPerUnitPriceDataCommand>
{
    
    @Inject
    private SalaryPerUnitPriceRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<SalaryPerUnitPriceDataCommand> context) {
        SalaryPerUnitPriceDataCommand command = context.getCommand();
        String cid = AppContexts.user().companyId();

        SalaryPerUnitPriceNameCommand name = command.getSalaryPerUnitPriceName();
        SalaryPerUnitPriceSettingCommand setting = command.getSalaryPerUnitPriceSetting();

        SalaryPerUnitPriceName salaryPerUnitPriceName = new SalaryPerUnitPriceName(cid, name.getCode(), name.getName(), name.getAbolition(), name.getShortName(), name.getIntegrationCode(), name.getNote());
        SalaryPerUnitPriceSetting salaryPerUnitPriceSetting = new SalaryPerUnitPriceSetting(cid, setting.getCode(), setting.getUnitPriceType(), setting.getSettingAtr(), setting.getTargetClassification(), setting.getMonthlySalary(), setting.getMonthlySalaryPerday(), setting.getDayPayee(), setting.getHourlyPay());

        Optional<SalaryPerUnitPrice> salaryPerUnitPrice = repository.getSalaryPerUnitPriceById(cid, name.getCode());

        if(salaryPerUnitPrice.isPresent()) {
            throw new BusinessException("Msg_3");
        }

        repository.add(new SalaryPerUnitPrice(salaryPerUnitPriceName, salaryPerUnitPriceSetting));
    
    }
}
