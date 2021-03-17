package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;

/**
 * 控除相殺時間
 * @author ken_takasu
 *
 */
@Getter
@NoArgsConstructor
public class DeductionOffSetTime  implements Cloneable {
	//年休
	private AttendanceTime annualLeave;
	//超過有休
	private AttendanceTime sixtyHourHoliday;
	//特別休暇
	private AttendanceTime SpecialHoliday;
	//代休
	private AttendanceTime CompensatoryLeave;

	/**
	 * Constructor 
	 */
	public DeductionOffSetTime(AttendanceTime annualLeave, AttendanceTime sixtyHourHoliday,
			AttendanceTime specialHoliday, AttendanceTime compensatoryLeave) {
		super();
		this.annualLeave = annualLeave;
		this.sixtyHourHoliday = sixtyHourHoliday;
		SpecialHoliday = specialHoliday;
		CompensatoryLeave = compensatoryLeave;
	}
	
	/**
	 * 全て0のインスタンスを生成
	 * @return DeductionOffSetTime （全て０）
	 */
	public static DeductionOffSetTime createAllZero() {
		return new DeductionOffSetTime(AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO);
	}
	
	/**
	 * 各相殺時間を合計した時間を返す
	 * @return
	 */
	public int getTotalOffSetTime() {
		int totalTime = this.annualLeave.valueAsMinutes()+this.sixtyHourHoliday.valueAsMinutes()+this.SpecialHoliday.valueAsMinutes()+this.CompensatoryLeave.valueAsMinutes();
		return totalTime;
	}
	
	public DeductionOffSetTime clone() {
		DeductionOffSetTime clone = new DeductionOffSetTime();
		try {
			clone.annualLeave = new AttendanceTime(this.annualLeave.valueAsMinutes());
			clone.sixtyHourHoliday = new AttendanceTime(this.sixtyHourHoliday.valueAsMinutes());
			clone.SpecialHoliday = new AttendanceTime(this.SpecialHoliday.valueAsMinutes());
			clone.CompensatoryLeave = new AttendanceTime(this.CompensatoryLeave.valueAsMinutes());
		}
		catch (Exception e) {
			throw new RuntimeException("DeductionOffSetTime clone error.");
		}
		return clone;
	}
}
