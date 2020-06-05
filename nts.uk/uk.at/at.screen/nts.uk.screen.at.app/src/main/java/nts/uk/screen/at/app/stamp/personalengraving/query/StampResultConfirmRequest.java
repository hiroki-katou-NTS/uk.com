package nts.uk.screen.at.app.stamp.personalengraving.query;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author anhdt
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StampResultConfirmRequest {
	private String stampDate;
	private List<Integer> attendanceItems;
	private String employeeId; // 2020/05/13　EA3769
	
	public DatePeriod toStampDatePeriod() {
		return new DatePeriod(GeneralDate.today(), GeneralDate.today());
	}
}
