package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 集計特別休暇日数
 * @author shuichu_ishida
 */
@Getter
public class AggregateSpcVacationDays {

	/** 特別休暇枠NO */
	private int spcVacationFrameNo;
	/** 日数 */
	private AttendanceDaysMonth days;
	/** 時間 */
	private AttendanceTimeMonth time;
	
	/**
	 * コンストラクタ
	 * @param spcVacationFrameNo 特別休暇枠NO
	 */
	public AggregateSpcVacationDays(int spcVacationFrameNo){
		
		this.spcVacationFrameNo = spcVacationFrameNo;
		this.days = new AttendanceDaysMonth(0.0);
		this.time = new AttendanceTimeMonth(0);
	}

	/**
	 * ファクトリー
	 * @param spcVacationFrameNo 特別休暇枠NO
	 * @param days 日数
	 * @param time 時間
	 * @return 集計特別休暇日数
	 */
	public static AggregateSpcVacationDays of(
			int spcVacationFrameNo,
			AttendanceDaysMonth days,
			AttendanceTimeMonth time){
		
		AggregateSpcVacationDays domain = new AggregateSpcVacationDays(spcVacationFrameNo);
		domain.days = days;
		domain.time = time;
		return domain;
	}
	
	/**
	 * 日数を加算する
	 * @param days 日数
	 */
	public void addDays(Double days){
		this.days = this.days.addDays(days);
	}
	
	/**
	 * 時間に分を加算する
	 * @param minutes 分
	 */
	public void addTime(int minutes){
		this.time = this.time.addMinutes(minutes);
	}
}
