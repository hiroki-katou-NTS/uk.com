package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import lombok.Getter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * RequestList No193 Import Class
 * @author keisuke_hoshina
 *
 */
@Getter
public class DailyExcessTotalTimePubImport {
	//社員ID
	String employeeId;
	//期間
	DatePeriod ymdSpan;
	
	/**
	 * Constructor 
	 */
	public DailyExcessTotalTimePubImport(String employeeId, DatePeriod ymdSpan) {
		super();
		this.employeeId = employeeId;
		this.ymdSpan = ymdSpan;
	}
	
	
}
