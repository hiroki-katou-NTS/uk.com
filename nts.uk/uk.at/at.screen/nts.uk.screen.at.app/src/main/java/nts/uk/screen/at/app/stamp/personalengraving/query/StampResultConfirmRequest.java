package nts.uk.screen.at.app.stamp.personalengraving.query;

import java.util.List;

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
	private List<Integer> attendanceItems;
	
	public DatePeriod toStampDatePeriod() {
		return new DatePeriod(GeneralDate.today(), GeneralDate.today());
	}
}
