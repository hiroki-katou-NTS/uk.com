package nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.wageprovision.unitpricename.PerUnitPriceName;

@Data
@AllArgsConstructor
public class PricePerItem {

    PerUnitPriceCode perUnitPriceCode;

    PerUnitPriceName perUnitPriceName;

    YearMonthPeriod period;

    SalaryUnitPrice individualUnitPrice;

}
