package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.specialsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;

/**
 * ３６協定年月設定
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
public class AgreementMonthSetting extends AggregateRoot {

    /** The employee id. */
    final String employeeId;

    /** 年月 */
    final YearMonth yearMonth;

    /** １ヶ月時間 */
    private ErrorTimeInMonth oneMonthTime;

}
