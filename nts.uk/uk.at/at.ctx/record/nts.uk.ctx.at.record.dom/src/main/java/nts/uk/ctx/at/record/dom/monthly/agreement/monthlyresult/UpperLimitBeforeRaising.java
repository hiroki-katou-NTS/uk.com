package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;

/**
 * 引き上げる前の上限
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
public class UpperLimitBeforeRaising {

    /** 1ヶ月の上限*/
    private ErrorTimeInMonth oneMonthLimit;

    /** 1年間の上限*/
    private ErrorTimeInYear oneYearLimit;

    /** 平均限度時間*/
    private AgreementOneMonthTime averageTimeLimit;

}
