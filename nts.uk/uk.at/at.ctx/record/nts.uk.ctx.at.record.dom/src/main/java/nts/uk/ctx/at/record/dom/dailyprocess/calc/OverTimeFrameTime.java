package nts.uk.ctx.at.record.dom.dailyprocess.calc;
/**
 * 残業枠時間
 * @author keisuke_hoshina
 *
 */

import lombok.Value;
import nts.uk.ctx.at.record.dom.daily.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;

@Value
public class OverTimeFrameTime {
	private OverTimeFrameNo OverWorkFrameNo;
	private TimeWithCalculation OverTimeWork;
	private TimeWithCalculation TransferTime;
	private AttendanceTime BeforeApplicationTime;
	
	/**
	 * 上限の時間から残業時間の差分をとる
	 * @param limitTime　上限の時間
	 * @return
	 */
//	public int calcLimit(int limitTime) {
//		return limitTime - overWorkFrae;
//	}
	
//	public boolean limitgreaterthanOverWorkTime(int limitTime) {|
//		if(limit>=OverWorkTime) {
//			return true;
//		}
//		else {
//			return false;
//		}
//	}

}
