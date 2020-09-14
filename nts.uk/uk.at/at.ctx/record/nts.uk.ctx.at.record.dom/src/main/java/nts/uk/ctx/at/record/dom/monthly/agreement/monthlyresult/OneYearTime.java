package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import nts.uk.ctx.at.shared.dom.common.Year;

/**
 * 年間時間
 * @author quang.nh1
 */
@Getter
@Setter
@AllArgsConstructor
public class OneYearTime {

    /**1年間時間*/
    private ErrorTimeInYear errorTimeInYear;

    /**年度*/
    private Year year;
}
