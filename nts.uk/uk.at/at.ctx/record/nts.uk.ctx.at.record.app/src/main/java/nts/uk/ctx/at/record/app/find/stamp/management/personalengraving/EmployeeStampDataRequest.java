package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author anhdt
 *
 */
@Data
public class EmployeeStampDataRequest {
	private String employeeId;
	private String startDate;
	private String endDate;
	
	public DatePeriod toDatePeriod() {
		GeneralDate startDate = GeneralDate.fromString(this.startDate, "yyyy/MM/dd");
		GeneralDate endDate = GeneralDate.fromString(this.endDate, "yyyy/MM/dd");
		return new DatePeriod(startDate, endDate);
	}
}
