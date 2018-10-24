package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 控除相殺時間
 * @author ken_takasu
 *
 */
@Getter
public class DeductionOffSetTime {
	//年休
	private AttendanceTime annualLeave;
	//積立年休
	private AttendanceTime retentionYearly;
	//特別休暇
	private AttendanceTime SpecialHoliday;
	//代休
	private AttendanceTime CompensatoryLeave;

	/**
	 * Constructor 
	 */
	public DeductionOffSetTime(AttendanceTime annualLeave, AttendanceTime retentionYearly,
			AttendanceTime specialHoliday, AttendanceTime compensatoryLeave) {
		super();
		this.annualLeave = annualLeave;
		this.retentionYearly = retentionYearly;
		SpecialHoliday = specialHoliday;
		CompensatoryLeave = compensatoryLeave;
	}	
	
	/**
	 * 各相殺時間を合計した時間を返す
	 * @return
	 */
	public int getTotalOffSetTime() {
		int totalTime = this.annualLeave.valueAsMinutes()+this.retentionYearly.valueAsMinutes()+this.SpecialHoliday.valueAsMinutes()+this.CompensatoryLeave.valueAsMinutes();
		return totalTime;
	}
}
