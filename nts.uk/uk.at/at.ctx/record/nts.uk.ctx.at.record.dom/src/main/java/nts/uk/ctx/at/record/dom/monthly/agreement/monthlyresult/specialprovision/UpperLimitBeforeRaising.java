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
    private OneMonthErrorAlarmTime oneMonthLimit;

    /** 1年間の上限*/
    private OneYearErrorAlarmTime oneYearLimit;

    /** 平均限度時間*/
    private AgreementOneMonthTime averageTimeLimit;

}
