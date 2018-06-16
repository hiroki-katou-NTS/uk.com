package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.exportparam.LateLeaveEarlyAtr;

/**
 *  RequestListNo197
 * @author keisuke_hoshina
 *
 */
@Getter
public class DailyLateAndLeaveEarlyTimePubExport {
	//年月日
	Map<GeneralDate,List<LateLeaveEarlyAtr>> map;

	/**
	 *  Constructor
	 */ 
	public DailyLateAndLeaveEarlyTimePubExport(Map<GeneralDate, List<LateLeaveEarlyAtr>> map) {
		super();
		this.map = map;
	}
}
