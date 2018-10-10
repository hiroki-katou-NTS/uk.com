package nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class SalaryPerUnitPriceDataDto {
    SalaryPerUnitPriceNameDto salaryPerUnitPriceName;
    SalaryPerUnitPriceSettingDto salaryPerUnitPriceSetting;
}
