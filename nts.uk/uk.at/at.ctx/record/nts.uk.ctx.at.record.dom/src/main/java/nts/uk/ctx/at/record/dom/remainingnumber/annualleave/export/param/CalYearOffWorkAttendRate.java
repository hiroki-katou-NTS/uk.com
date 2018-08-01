package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 年休出勤率計算結果
 * @author shuichu_ishida
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalYearOffWorkAttendRate {
	
	/** 出勤率 */
	private Double attendanceRate;
	
	/** 所定日数 */
	private Double prescribedDays;
	
	/** 労働日数 */
	private Double workingDays;
	
	/** 控除日数 */
	private Double deductedDays;

	/**
	 * 日数から出勤率を計算する
	 */
	public void calcAttendanceRate(){

		if (this.prescribedDays == null) this.prescribedDays = 0.0;
		if (this.workingDays == null) this.workingDays = 0.0;
		if (this.deductedDays == null) this.deductedDays = 0.0;
		
		// 出勤率を計算
		Double totalPrescribed = this.prescribedDays - this.deductedDays;
		Double attendanceRate = 0.0;
		if (totalPrescribed != 0.0){
			attendanceRate = this.workingDays / totalPrescribed;
		}
		
		// 小数点第2位で切り捨て
		attendanceRate = attendanceRate * 100;
		Integer intRate = attendanceRate.intValue();
		attendanceRate = intRate.doubleValue() / 100;
		
		// 計算した出勤率をチェック
		if (attendanceRate > 100.0) attendanceRate = 100.0;
		
		// 出勤率を返す
		this.attendanceRate = attendanceRate;
	}
}
