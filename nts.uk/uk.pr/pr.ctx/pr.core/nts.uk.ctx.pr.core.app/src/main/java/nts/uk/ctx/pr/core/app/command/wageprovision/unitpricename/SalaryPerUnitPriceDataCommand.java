package nts.uk.ctx.pr.core.app.command.wageprovision.unitpricename;

import lombok.Value;

@Value
public class SalaryPerUnitPriceDataCommand {
    boolean checkCreate;
    SalaryPerUnitPriceNameCommand salaryPerUnitPriceName;
    SalaryPerUnitPriceSettingCommand salaryPerUnitPriceSetting;
}
