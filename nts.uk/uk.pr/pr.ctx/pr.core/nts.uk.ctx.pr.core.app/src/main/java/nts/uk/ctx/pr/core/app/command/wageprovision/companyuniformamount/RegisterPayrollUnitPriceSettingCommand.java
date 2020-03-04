package nts.uk.ctx.pr.core.app.command.wageprovision.companyuniformamount;


import lombok.Value;

@Value
public class RegisterPayrollUnitPriceSettingCommand {

    private PayrollUnitPriceCommand payrollUnitPriceCommand;

    private PayrollUnitPriceHistoryCommand payrollUnitPriceHistoryCommand;

    private PayrollUnitPriceSettingCommand payrollUnitPriceSettingCommand;
}
