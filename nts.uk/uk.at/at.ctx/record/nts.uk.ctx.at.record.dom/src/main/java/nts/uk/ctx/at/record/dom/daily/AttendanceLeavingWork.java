package nts.uk.ctx.at.record.dom.daily;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 出退勤
 * @author keisuke_hoshina
 *
 */
@Value
public class AttendanceLeavingWork {
	private WorkStampWithActualStamp attendance;
	private WorkStampWithActualStamp leaveWork;
	private int workNo;
	
	/**
	 * 出勤時刻と退勤時刻から計算用時間帯クラス作成
	 * @return　計算用時間帯クラス
	 */
	public TimeSpanForCalc getTimeSpan() {
		return new TimeSpanForCalc(attendance.getEngrave().getTimesOfDay()
								  ,leaveWork.getEngrave().getTimesOfDay());
	}
}
