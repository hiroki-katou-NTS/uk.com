package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * 1ヶ月時間
 * @author quang.nh1
 */
@Getter
@Setter
@AllArgsConstructor
public class OneMonthTime extends ValueObject {

    /**1ヶ月時間*/
    private OneMonthErrorAlarmTime errorTimeInMonth;

    /**年月度*/
    private final YearMonth yearMonth;

    /**
     * [C-0] 1ヶ月時間 (年月度,1ヶ月時間)
     */
    public static OneMonthTime create(OneMonthErrorAlarmTime errorTimeInMonth, YearMonth yearMonth) {

        return new OneMonthTime(errorTimeInMonth, yearMonth);
    }
}
