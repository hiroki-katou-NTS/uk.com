package nts.uk.ctx.pr.core.app.command.wageprovision.empsalunitprice;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class UpdateIndEmpSalUnitPriceHistoryCommand {

    private String personalUnitPriceCode;

    private String employeeId;

    private String historyId;

    private int startYearMonth;

    private int endYearMonth;

    private String lastHistoryId;

    private int lastStartYearMonth;

    private int lastEndYearMonth;

}
