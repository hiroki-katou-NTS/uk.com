package nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class WorkIndividualPrice {

    String historyID;

    String employeeID;

    String businessName;

    String employeeCode;

    int startYaerMonth;

    int endYearMonth;

    BigDecimal amountOfMoney;

}
