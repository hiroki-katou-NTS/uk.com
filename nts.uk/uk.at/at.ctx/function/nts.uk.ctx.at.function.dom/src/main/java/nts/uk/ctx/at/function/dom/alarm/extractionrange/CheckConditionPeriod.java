package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import java.util.Date;

import lombok.Value;

@Value
public class CheckConditionPeriod {
	private Date startDate;
	private Date endDate;
}
