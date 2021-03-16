package nts.uk.ctx.at.record.pub.company;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.calendar.period.DatePeriod;
@Getter
@Setter
@AllArgsConstructor
public class StatusOfEmployeeExportPub {
	
	
	private String employeeId;

	private List<DatePeriod> listPeriod;
}
