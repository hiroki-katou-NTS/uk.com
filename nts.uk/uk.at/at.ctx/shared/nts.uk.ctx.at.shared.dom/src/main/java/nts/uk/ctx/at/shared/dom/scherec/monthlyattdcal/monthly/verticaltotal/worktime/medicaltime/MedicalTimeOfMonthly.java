package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.medicaltime;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;

/**
 * 月別実績の医療時間
 * @author shuichi_ishida
 */
@Getter
public class MedicalTimeOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 日勤夜勤区分 */
	private WorkTimeNightShift dayNightAtr;
	
	/** 勤務時間 */
	private AttendanceTimeMonth workTime;
	/** 控除時間 */
	private AttendanceTimeMonth deducationTime;
	/** 申送時間 */
	private AttendanceTimeMonth takeOverTime;
	
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
	 * 勤務時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToWorkTime(int minutes){
		this.workTime = this.workTime.addMinutes(minutes);
	}
	
	/**
	 * 控除時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToDeducationTime(int minutes){
		this.deducationTime = this.deducationTime.addMinutes(minutes);
	}
	
	/**
	 * 申送時間に分を加算する
	 * @param minutes 分
	 */
	public void addMinutesToTakeOverTime(int minutes){
		this.takeOverTime = this.takeOverTime.addMinutes(minutes);
	}
}
