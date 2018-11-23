package nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class IndEmpSalUnitPriceHistory {

    String personalUnitPriceCode;

    String employeeId;

    String historyId;

    int startYearMonth;

    int endYearMonth;

    BigDecimal amountOfMoney;

}
