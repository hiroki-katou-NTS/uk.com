package nts.uk.ctx.alarm.dom.check.context;

import lombok.Value;
import nts.uk.ctx.alarm.dom.check.context.period.CheckingPeriod;

@Value
public class CheckingContext {

    String targetEmployeeId;

    CheckingPeriod checkingPeriod;
}
