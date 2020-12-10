package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;


/**
 * 引き上げる前の上限
 * @author quang.nh1
 */
@Getter
@AllArgsConstructor
public class UpperLimitBeforeRaising {

    /** 1ヶ月の上限*/
    private final OneMonthErrorAlarmTime oneMonthLimit;

    /** 1年間の上限*/
    private final OneYearErrorAlarmTime oneYearLimit;

    /** 平均限度時間*/
    private final AgreementOneMonthTime averageTimeLimit;

    /**
     * 	[C-0] 引き上げる前の上限 (1ヶ月の上限,1年間の上限,平均限度時間)
     */
    public static UpperLimitBeforeRaising create(OneMonthErrorAlarmTime oneMonthLimit, OneYearErrorAlarmTime oneYearLimit,AgreementOneMonthTime averageTimeLimit) {
        return new UpperLimitBeforeRaising(oneMonthLimit, oneYearLimit,averageTimeLimit);
    }

}
