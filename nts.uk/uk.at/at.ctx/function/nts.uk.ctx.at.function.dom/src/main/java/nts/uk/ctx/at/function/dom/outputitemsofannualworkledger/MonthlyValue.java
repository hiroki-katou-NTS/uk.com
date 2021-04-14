package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;

/**
 * 月次の値
 */
@Getter
@Setter
@AllArgsConstructor
public class MonthlyValue {

    // 実績値
    private Double actualValue;

    // 文字値
    private String characterValue;

    // 年月
    private YearMonth date;

}
