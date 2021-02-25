package nts.uk.ctx.at.schedule.app.command.executionlog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
@Getter
public class StateAndValueDatePeriod {
	public DatePeriod value;
	public StateValueDate state;
}
