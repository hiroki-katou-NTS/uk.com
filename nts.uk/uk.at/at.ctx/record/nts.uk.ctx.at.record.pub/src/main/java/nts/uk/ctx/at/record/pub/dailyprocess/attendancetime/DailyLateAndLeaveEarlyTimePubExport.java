package nts.uk.ctx.at.record.pub.dailyprocess.attendancetime;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.record.pub.dailyprocess.attendancetime.exportparam.LateLeaveEarlyManage;

/**
 *  RequestListNo197
 * @author keisuke_hoshina
 *
 */
@Getter
public class DailyLateAndLeaveEarlyTimePubExport {
	//Output list
	List<LateLeaveEarlyManage> list;
	
	/**
	 * Constructor
	 */
	public DailyLateAndLeaveEarlyTimePubExport(List<LateLeaveEarlyManage> list) {
		super();
		this.list = list;
	}
}
