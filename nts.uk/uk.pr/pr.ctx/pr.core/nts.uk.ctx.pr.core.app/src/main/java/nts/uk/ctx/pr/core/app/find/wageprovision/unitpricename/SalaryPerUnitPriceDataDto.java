package nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPrice;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.SalaryPerUnitPriceName;

@AllArgsConstructor
@Value
public class SalaryPerUnitPriceDataDto {
    SalaryPerUnitPriceNameDto salaryPerUnitPriceName;
    SalaryPerUnitPriceSettingDto salaryPerUnitPriceSetting;

    public SalaryPerUnitPriceDataDto (SalaryPerUnitPrice domain)
    {
        this.salaryPerUnitPriceName = SalaryPerUnitPriceNameDto.fromDomain(domain.getSalaryPerUnitPriceName());
        this.salaryPerUnitPriceSetting = new SalaryPerUnitPriceSettingDto(domain.getSalaryPerUnitPriceSetting());
    }
}
