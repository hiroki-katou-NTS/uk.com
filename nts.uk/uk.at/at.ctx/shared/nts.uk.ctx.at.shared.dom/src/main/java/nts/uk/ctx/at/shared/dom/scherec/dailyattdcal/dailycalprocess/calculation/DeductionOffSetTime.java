package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation;

import lombok.Getter;
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
}
