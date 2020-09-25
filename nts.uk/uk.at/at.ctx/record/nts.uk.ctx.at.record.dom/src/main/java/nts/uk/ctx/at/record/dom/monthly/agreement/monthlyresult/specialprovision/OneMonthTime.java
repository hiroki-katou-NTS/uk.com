package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;

/**
 * 1ヶ月時間
 * @author quang.nh1
 */
@Getter
@Setter
@AllArgsConstructor
public class OneMonthTime {

    /**1ヶ月時間*/
    private ErrorTimeInMonth errorTimeInMonth;

    /**年月	 */
    private YearMonth yearMonth;
}
