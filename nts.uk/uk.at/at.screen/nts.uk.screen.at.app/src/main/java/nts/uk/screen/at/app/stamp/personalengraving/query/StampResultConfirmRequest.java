package nts.uk.screen.at.app.stamp.personalengraving.query;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author anhdt
 *
 */
@Data
public class StampResultConfirmRequest {
	private String stampDate;
	
	
	public DatePeriod toStampDatePeriod() {
		return new DatePeriod(GeneralDate.fromString(stampDate, "yyyy/MM/dd"), GeneralDate.fromString(stampDate, "yyyy/MM/dd"));
	}
}
