package nts.uk.ctx.pr.core.dom.wageprovision.unitpricename;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalaryPerUnitPrice {
    SalaryPerUnitPriceName salaryPerUnitPriceName;
    SalaryPerUnitPriceSetting salaryPerUnitPriceSetting;

    public SalaryPerUnitPrice(SalaryPerUnitPriceName salaryPerUnitPriceName, SalaryPerUnitPriceSetting salaryPerUnitPriceSetting) {
        this.salaryPerUnitPriceName = salaryPerUnitPriceName;
        this.salaryPerUnitPriceSetting = salaryPerUnitPriceSetting;
    }
}
