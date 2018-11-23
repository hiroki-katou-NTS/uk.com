package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class IndEmpSalUnitPriceHistoryCommand {

    private String personalUnitPrice;

    private String employeeId;

    private String historyId;

    private int startYearMonth;

    private int endYearMonth;

    private BigDecimal amountOfMoney;

    private String oldHistoryId;

    private int newEndYearMonth;

}
