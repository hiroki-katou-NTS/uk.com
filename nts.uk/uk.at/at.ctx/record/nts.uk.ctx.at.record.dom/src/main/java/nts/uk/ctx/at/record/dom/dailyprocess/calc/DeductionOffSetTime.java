package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 控除相殺時間
 * @author ken_takasu
 *
 */
@Value
public class DeductionOffSetTime {
	private AttendanceTime annualLeave;
	private AttendanceTime retentionYearly;
	private AttendanceTime SpecialHoliday;
	private AttendanceTime CompensatoryLeave;
	
	
	/**
	 * 各相殺時間を合計した時間を返す
	 * @return
	 */
	public int getTotalOffSetTime() {
		int totalTime = this.annualLeave.valueAsMinutes()+this.retentionYearly.valueAsMinutes()+this.SpecialHoliday.valueAsMinutes()+this.CompensatoryLeave.valueAsMinutes();
		return totalTime;
	}
	
}
