package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving;

import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * @author anhdt
 *
 */
@Data
public class EmployeeTimeCardRequest {
	private String date;

	public GeneralDate toDatePeriod() {
		GeneralDate date = GeneralDate.fromString(this.date, "yyyy/MM/dd");
		return date;
	}
}
