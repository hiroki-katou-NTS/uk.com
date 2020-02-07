package nts.uk.ctx.pr.transfer.dom.adapter.core.emprsdttaxinfo.amountinfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.Year;

@Setter
@Getter
@NoArgsConstructor
public class EmployeeResidentTaxPayAmountInfoImport {

    /**
     * 社員ID
     */
    private String sid;

    /**
     * 年度
     */
    private Year year;

    /**
     * 入力区分
     */
    private int inputAtr;

    /**
     * 月次納付額
     */
    private MonthlyResidentTaxPayAmountImport monthlyPaymentAmount;
}
