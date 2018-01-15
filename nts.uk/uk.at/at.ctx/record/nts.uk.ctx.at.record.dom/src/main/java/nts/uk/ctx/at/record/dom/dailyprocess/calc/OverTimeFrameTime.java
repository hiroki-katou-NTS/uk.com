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
	/** 残業枠NO: 残業枠NO */
	private OverTimeFrameNo OverWorkFrameNo;
	/** 残業時間: 計算付き時間 */
	private TimeWithCalculation OverTimeWork;
	/** 振替時間: 計算付き時間 */
	private TimeWithCalculation TransferTime;
	/** 事前申請時間: 勤怠時間 */
	private AttendanceTime BeforeApplicationTime;
	/** 指示時間: 勤怠時間 */
	private AttendanceTime orderTime;
	
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
