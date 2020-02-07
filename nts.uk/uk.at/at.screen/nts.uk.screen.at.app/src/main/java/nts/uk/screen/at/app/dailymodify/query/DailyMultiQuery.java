package nts.uk.screen.at.app.dailymodify.query;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;

@Data
@NoArgsConstructor
public class DailyMultiQuery {

	private List<String> employeeIds;
	
	private DatePeriod period;

	public DailyMultiQuery(List<String> employeeIds, DatePeriod period) {
		super();
		this.employeeIds = employeeIds;
		this.period = period;
	}
}
