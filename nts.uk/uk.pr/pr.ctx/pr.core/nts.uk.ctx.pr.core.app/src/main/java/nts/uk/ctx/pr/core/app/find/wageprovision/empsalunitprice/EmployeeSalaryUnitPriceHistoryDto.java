package nts.uk.ctx.pr.core.app.find.wageprovision.empsalunitprice;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.empsalunitprice.EmployeeSalaryUnitPriceHistory;

import java.util.List;

/**
 * 社員給与単価履歴 : DTO
 */

@AllArgsConstructor
@Value
public class EmployeeSalaryUnitPriceHistoryDto {
    /**
     * 個人単価コード
     */
    private String personalUnitPriceCode;

    /**
     * 社員ID
     */
    private String employeeID;

    /**
     * 期間
     */

    List<PeriodAndUnitPriceDto> periodAndUnitPriceDtoList;

    public static EmployeeSalaryUnitPriceHistoryDto fromDomain(EmployeeSalaryUnitPriceHistory domain) {
        if(domain==null)
            return null;
        return new EmployeeSalaryUnitPriceHistoryDto(
                domain.getPersonalUnitPriceCode().v(),
                domain.getEmployeeID(),
                PeriodAndUnitPriceDto.fromDomain(domain.getPeriod())
        );
    }
}
