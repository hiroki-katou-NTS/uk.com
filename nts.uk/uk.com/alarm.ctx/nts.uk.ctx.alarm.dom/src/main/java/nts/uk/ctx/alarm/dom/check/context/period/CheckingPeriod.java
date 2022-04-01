package nts.uk.ctx.alarm.dom.check.context.period;

import lombok.Value;

/**
 * チェック対象期間
 */
@Value
public class CheckingPeriod {

    CheckingPeriodDaily periodDaily;

    CheckingPeriodMonthly periodMonthly;
}
