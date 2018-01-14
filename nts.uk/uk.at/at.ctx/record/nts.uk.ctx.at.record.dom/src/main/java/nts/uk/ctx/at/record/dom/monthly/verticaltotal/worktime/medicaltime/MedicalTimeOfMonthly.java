package nts.uk.ctx.at.record.dom.monthly.verticaltotal.worktime.medicaltime;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

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
	private int dayNightAtr;
	
	/**
	 * コンストラクタ
	 */
	public MedicalTimeOfMonthly(){
		
		this.workTime = new AttendanceTimeMonth(0);
		this.deducationTime = new AttendanceTimeMonth(0);
		this.takeOverTime = new AttendanceTimeMonth(0);
		this.dayNightAtr = 0;
	}

	/**
	 * ファクトリー
	 * @param workTime 勤務時間
	 * @param deducationTime 控除時間
	 * @param takeOverTime 申送時間
	 * @param dayNightAtr 日勤夜勤区分
	 * @return 月別実績の医療時間
	 */
	public static MedicalTimeOfMonthly of(
			AttendanceTimeMonth workTime,
			AttendanceTimeMonth deducationTime,
			AttendanceTimeMonth takeOverTime,
			int dayNightAtr){
		
		val domain = new MedicalTimeOfMonthly();
		domain.workTime = workTime;
		domain.deducationTime = deducationTime;
		domain.takeOverTime = takeOverTime;
		domain.dayNightAtr = dayNightAtr;
		return domain;
	}
}
