package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import java.util.Map;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.importparam.DailyLateAndLeaveEarlyTimePubImpParam;
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
	
	//日付、表示のBoolean
	Map<GeneralDate,DailyLateAndLeaveEarlyTimePubImpParam> booleanParam;
	
	/**
	 * Constructor 
	 */
	public DailyLateAndLeaveEarlyTimePubImport(String employeeId,
			Map<GeneralDate, DailyLateAndLeaveEarlyTimePubImpParam> booleanParam) {
		super();
		this.employeeId = employeeId;
		this.booleanParam = booleanParam;
	}
}
