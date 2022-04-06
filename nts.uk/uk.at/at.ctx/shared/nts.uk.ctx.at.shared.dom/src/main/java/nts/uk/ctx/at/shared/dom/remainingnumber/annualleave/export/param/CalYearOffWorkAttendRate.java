package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.days.YearlyDays;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AttendanceRate;

/**
 * 年休出勤率計算結果
 * @author shuichi_ishida
 */
@Data
@AllArgsConstructor
public class CalYearOffWorkAttendRate {
	
	/** 出勤率 */
	private AttendanceRate attendanceRate;
	
	/** 所定日数 */
	private YearlyDays prescribedDays;
	
	/** 労働日数 */
	private YearlyDays workingDays;
	
	/** 控除日数 */
	private YearlyDays deductedDays;
	
	/** 出勤率計算期間 */
	private Optional<DatePeriod> period;

	public CalYearOffWorkAttendRate(){
		this.prescribedDays = new YearlyDays(0.0);
		this.workingDays = new YearlyDays(0.0);
		this.deductedDays = new YearlyDays(0.0);
		this.period = Optional.empty();
		calcAttendanceRate();
	}
	
	public CalYearOffWorkAttendRate(double prescribedDays,
			double workingDays, double deductedDays, Optional<DatePeriod> period){
		this.prescribedDays = new YearlyDays(prescribedDays);
		this.workingDays = new YearlyDays(workingDays);
		this.deductedDays = new YearlyDays(deductedDays);
		this.period = period;
		calcAttendanceRate();
	}
	
	
	public CalYearOffWorkAttendRate(double attendanceRate, double prescribedDays,
			double workingDays, double deductedDays, Optional<DatePeriod> period){
		this.attendanceRate = new AttendanceRate(attendanceRate);
		this.prescribedDays = new YearlyDays(prescribedDays);
		this.workingDays = new YearlyDays(workingDays);
		this.deductedDays = new YearlyDays(deductedDays);
		this.period = period;
	}
	
	/**
	 * 日数から出勤率を計算する
	 */
	public void calcAttendanceRate(){

		if (this.prescribedDays == null) this.prescribedDays = new YearlyDays(0.0);
		if (this.workingDays == null) this.workingDays = new YearlyDays(0.0);
		if (this.deductedDays == null) this.deductedDays = new YearlyDays(0.0);
		
		// 出勤率を計算
		Double totalPrescribed = this.prescribedDays.v() - this.deductedDays.v();
		Double attendanceRate = 0.0;
		if (totalPrescribed != 0.0){
			attendanceRate = this.workingDays.v() / totalPrescribed;
		}
		
		// 小数点第3位で切り捨て　→　100倍する（％値にする）
		attendanceRate = attendanceRate * 1000.0;
		Integer intRate = attendanceRate.intValue();
		attendanceRate = intRate.doubleValue() / 10.0;
		
		// 計算した出勤率をチェック
		if (attendanceRate > 100.0) attendanceRate = 100.0;
		
		// 出勤率を返す
		this.attendanceRate = new AttendanceRate(attendanceRate);
	}
}
