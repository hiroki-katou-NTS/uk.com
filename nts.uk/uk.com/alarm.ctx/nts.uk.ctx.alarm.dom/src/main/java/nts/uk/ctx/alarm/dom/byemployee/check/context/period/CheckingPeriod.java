package nts.uk.ctx.alarm.dom.byemployee.check.context.period;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;

/**
 * チェック対象期間
 */
@Value
public class CheckingPeriod {

    /** 見込み月次 */
    CheckingPeriodMonthly prospectMonthly;
    
    /** 見込み年次 */
    CheckingPeriodMonthly prospectYearly;

    /** 日次 */
    CheckingPeriodDaily daily;

    /** 週次 */
    CheckingPeriodWeekly weekly;

    /** 月次 */
    CheckingPeriodMonthly monthly;
    
    /** 任意期間 */
    AnyAggrFrameCode anyPeriod;

    /** 休暇 */
    CheckingPeriodVacation vacation;
}
