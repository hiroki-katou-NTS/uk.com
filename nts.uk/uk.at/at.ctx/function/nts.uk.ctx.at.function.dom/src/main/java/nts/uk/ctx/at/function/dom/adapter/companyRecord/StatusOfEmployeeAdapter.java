package nts.uk.ctx.at.function.dom.adapter.companyRecord;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StatusOfEmployeeAdapter {
	private String employeeId;

	private List<DatePeriod> listPeriod;
}
