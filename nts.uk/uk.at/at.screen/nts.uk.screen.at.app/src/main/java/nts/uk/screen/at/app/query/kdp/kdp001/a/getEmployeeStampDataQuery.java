package nts.uk.screen.at.app.query.kdp.kdp001.a;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@AllArgsConstructor
@NoArgsConstructor
public class getEmployeeStampDataQuery {
	private String startDate;
	private String endDate;

	public DatePeriod getPeriod() {

		return new DatePeriod(GeneralDate.fromString(startDate, "yyyy/MM/dd"),
				GeneralDate.fromString(endDate, "yyyy/MM/dd"));
	}
}

