package nts.uk.ctx.alarm.dom.byemployee.check.context.period;

import lombok.Value;

/**
 * チェック対象期間
 */
@Value
public class CheckingPeriod {

    /** スケジュール月次 */
    CheckingPeriodMonthly scheduleMonthly;

    /** 日次 */
    CheckingPeriodDaily daily;

    /** 月次 */
    CheckingPeriodMonthly monthly;
    
    /** ３６協定月次 */
    CheckingPeriodMonthlyAgreement monthlyAgreement;
    
    /** ３６協定年次 */
    CheckingPeriodYearlyAgreement yearlyAgreement;
}
