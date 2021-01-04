package nts.uk.screen.at.app.command.kmk.kmk004.common.monthlyworktimesetcom;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;

/**
 * 
 * @author sonnlb
 *
 */
@Value
@AllArgsConstructor
public class YearMonthPeriodCommand {
	private int start;
	private int end;

	public YearMonthPeriod toDomain() {

		return new YearMonthPeriod(new YearMonth(start), new YearMonth(end));
	}
}
