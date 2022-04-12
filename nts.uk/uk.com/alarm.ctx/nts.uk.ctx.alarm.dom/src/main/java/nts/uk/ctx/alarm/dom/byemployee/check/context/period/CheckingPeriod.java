package nts.uk.ctx.alarm.dom.byemployee.check.context.period;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.scherec.byperiod.anyaggrperiod.AnyAggrFrameCode;

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
    
    /** 任意期間 */
    AnyAggrFrameCode anyPeriod;
}
