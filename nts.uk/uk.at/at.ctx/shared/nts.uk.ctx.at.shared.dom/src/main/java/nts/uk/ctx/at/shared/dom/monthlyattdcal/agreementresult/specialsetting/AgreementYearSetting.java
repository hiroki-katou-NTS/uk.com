package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.specialsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;

/**
 * ３６協定年度設定
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
public class AgreementYearSetting extends AggregateRoot {

    /** The employee id. */
    final String employeeId;

    /** 年度 */
    final Year year;

    /** １年間時間 */
    private ErrorTimeInYear errorTimeInYear;

}
