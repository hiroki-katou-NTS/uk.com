package nts.uk.ctx.alarm.dom.byemployee.check.context;

import lombok.Value;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriod;

/**
 * チェックコンテキスト(社員別)
 */
@Value
public class CheckingContextByEmployee {

    String targetEmployeeId;

    CheckingPeriod checkingPeriod;
}
