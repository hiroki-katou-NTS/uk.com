package nts.uk.screen.at.ws.shift.management.workavailability;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author quytb
 *
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ksu001gRequest {
	private String startDate;
	private String endDate;
	private List<String> employeeIDs;

	public DatePeriod toPeriod() {
		return new DatePeriod(GeneralDate.fromString(startDate, "yyyy/MM/dd"),
				GeneralDate.fromString(endDate, "yyyy/MM/dd"));
	}
}
