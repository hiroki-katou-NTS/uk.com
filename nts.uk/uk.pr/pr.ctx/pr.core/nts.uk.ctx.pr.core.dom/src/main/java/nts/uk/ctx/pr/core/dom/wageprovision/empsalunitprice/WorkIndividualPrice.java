package nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class WorkIndividualPrice {

    String employeeID;

    String historyID;

    String employeeCode;

    String businessName;

    int startYaerMonth;

    int endYearMonth;

    BigDecimal amountOfMoney;

}
