package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import org.eclipse.persistence.internal.xr.ValueObject;

/**
 * 年間時間
 * @author quang.nh1
 */
@Getter
@Setter
@AllArgsConstructor
public class OneYearTime extends ValueObject{

    /**1年間時間*/
    private OneYearErrorAlarmTime errorTimeInYear;

    /**年度*/
    private final Year year;

    /**
     * [C-0] 年間時間 (年度,1年間時間)
     */
    public static OneYearTime create(OneYearErrorAlarmTime errorTimeInYear, Year year) {

        return new OneYearTime(errorTimeInYear, year);
    }
}
