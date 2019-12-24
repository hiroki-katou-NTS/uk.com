package nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.IndEmpSalUnitPriceHistory;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class IndEmpSalUnitPriceHistoryDto {

    private String personalUnitPriceCode;

    private String employeeId;

    private String historyId;

    private int startYearMonth;

    private int endYearMonth;

    private BigDecimal amountOfMoney;

    public static IndEmpSalUnitPriceHistoryDto fromDomainToDto(IndEmpSalUnitPriceHistory domain) {
        return new IndEmpSalUnitPriceHistoryDto(
                domain.getPersonalUnitPriceCode(),
                domain.getEmployeeId(),
                domain.getHistoryId(),
                domain.getStartYearMonth(),
                domain.getEndYearMonth(),
                domain.getAmountOfMoney()
        );
    }
}
