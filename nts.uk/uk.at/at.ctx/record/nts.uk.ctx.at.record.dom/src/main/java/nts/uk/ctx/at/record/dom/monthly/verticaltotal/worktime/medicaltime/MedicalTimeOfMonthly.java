package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.medicaltime;

import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;

/**
 * 月別実績の医療時間
 * @author shuichu_ishida
 */
@Getter
public class MedicalTimeOfMonthly {

	/** 勤務時間 */
	private AttendanceTimeMonth workTime;
	/** 控除時間 */
	private AttendanceTimeMonth deducationTime;
	/** 申送時間 */
	private AttendanceTimeMonth takeOverTime;
	/** 日勤夜勤区分 */
	private WorkTimeNightShift dayNightAtr;
	
	/**
	 * コンストラクタ
	 * @param dayNightAtr 日勤夜勤区分
	 */
	public MedicalTimeOfMonthly(WorkTimeNightShift dayNightAtr){
		
		this.dayNightAtr = dayNightAtr;
		this.workTime = new AttendanceTimeMonth(0);
		this.deducationTime = new AttendanceTimeMonth(0);
		this.takeOverTime = new AttendanceTimeMonth(0);
	}

	/**
	 * ファクトリー
	 * @param dayNightAtr 日勤夜勤区分
	 * @param workTime 勤務時間
	 * @param deducationTime 控除時間
	 * @param takeOverTime 申送時間
	 * @return 月別実績の医療時間
	 */
	public static MedicalTimeOfMonthly of(
			WorkTimeNightShift dayNightAtr,
			AttendanceTimeMonth workTime,
			AttendanceTimeMonth deducationTime,
			AttendanceTimeMonth takeOverTime){
		
		val domain = new MedicalTimeOfMonthly(dayNightAtr);
		domain.workTime = workTime;
		domain.deducationTime = deducationTime;
		domain.takeOverTime = takeOverTime;
		return domain;
	}
	
	/**
	 * 集計
	 * @param attendanceTimeOfDailys 日別実績の勤怠時間リスト
	 */
	public void aggregate(List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys){
		
		this.workTime = new AttendanceTimeMonth(0);
		this.deducationTime = new AttendanceTimeMonth(0);
		this.takeOverTime = new AttendanceTimeMonth(0);
		
		for (val attendanceTimeOfDaily : attendanceTimeOfDailys){
			//val medicalCareTime = attendanceTimeOfDaily.getMedicalCareTime();
			//*****（未）　医療時間の集計単位の確認要。
		}
	}
}
