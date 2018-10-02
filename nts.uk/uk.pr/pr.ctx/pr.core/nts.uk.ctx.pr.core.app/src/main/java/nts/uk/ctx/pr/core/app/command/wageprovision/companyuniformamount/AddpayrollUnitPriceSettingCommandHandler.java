package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceSetting;
import nts.uk.ctx.pr.core.wageprovision.companyuniformamount.PayrollUnitPriceSettingRepository;

@Stateless
@Transactional
public class AddpayrollUnitPriceSettingCommandHandler extends CommandHandler<PayrollUnitPriceSettingCommand>
{
    
    @Inject
    private PayrollUnitPriceSettingRepository repository;
    
    @Override
    protected void handle(CommandHandlerContext<PayrollUnitPriceSettingCommand> context) {
        PayrollUnitPriceSettingCommand command = context.getCommand();
        repository.add(new PayrollUnitPriceSetting(command.getHistoryId(), command.getAmountOfMoney(), command.getTargetClass(), command.getMonthSalaryPerDay(), command.getADayPayee(), command.getHourlyPay(), command.getMonthlySalary(), command.getSetClassification(), command.getNotes()));
    }
}
