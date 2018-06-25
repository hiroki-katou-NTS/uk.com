package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.importparam;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.exportparam.LateLeaveEarlyAtr;

/**
 * RequestList No.197
 * @author keisuke_hoshina
 *
 */
@Getter
public class DailyLateAndLeaveEarlyTimePubImpParam {
	//遅刻表示1
	boolean isDisplayLate1;
	//遅刻表示2
	boolean isDisplayLate2;
	//早退表示1
	boolean isDisplayLeaveEarly1;
	//早退表示2
	boolean isDisplayLeaveEarly2;
	
	/**
	 * Constructor 
	 */
	public DailyLateAndLeaveEarlyTimePubImpParam(boolean isDisplayLate1, boolean isDisplayLate2,
			boolean isDisplayLeaveEarly1, boolean isDisplayLeaveEarly2) {
		super();
		this.isDisplayLate1 = isDisplayLate1;
		this.isDisplayLate2 = isDisplayLate2;
		this.isDisplayLeaveEarly1 = isDisplayLeaveEarly1;
		this.isDisplayLeaveEarly2 = isDisplayLeaveEarly2;
	}
}
