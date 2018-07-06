package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import lombok.Getter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * RequestList No 197
 * @author keisuke_hoshina
 *
 */
@Getter
public class DailyLateAndLeaveEarlyTimePubImport {
	
	//社員ID
	String employeeId;
	
	//年月日
	DatePeriod daterange;
	
	/**
	 * Constructor 
	 */
	public DailyLateAndLeaveEarlyTimePubImport(String employeeId,
			DatePeriod daterange) {
		super();
		this.employeeId = employeeId;
		this.daterange = daterange;
	}
}
