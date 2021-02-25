package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.worktime.holidaytime;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * 月別実績の休日時間
 * @author shuichi_ishida
 */
@Getter
public class HolidayTimeOfMonthly implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 法定内休日時間 */
	private AttendanceTimeMonth legalHolidayTime;
	/** 法定外休日時間 */
	private AttendanceTimeMonth illegalHolidayTime;
	/** 法定外祝日休日時間 */
	private AttendanceTimeMonth illegalSpecialHolidayTime;
	
	/**
	 * コンストラクタ
	 */
	public HolidayTimeOfMonthly(){
		
		this.legalHolidayTime = new AttendanceTimeMonth(0);
		this.illegalHolidayTime = new AttendanceTimeMonth(0);
		this.illegalSpecialHolidayTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param legalHolidayTime 法定内休日時間
	 * @param illegalHolidayTime 法定外休日時間
	 * @param illegalSpecialHolidayTime 法定外祝日休日時間
	 * @return
	 */
	public static HolidayTimeOfMonthly of(
			AttendanceTimeMonth legalHolidayTime,
			AttendanceTimeMonth illegalHolidayTime,
			AttendanceTimeMonth illegalSpecialHolidayTime){
		
		val domain = new HolidayTimeOfMonthly();
		domain.legalHolidayTime = legalHolidayTime;
		domain.illegalHolidayTime = illegalHolidayTime;
		domain.illegalSpecialHolidayTime = illegalSpecialHolidayTime;
		return domain;
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(HolidayTimeOfMonthly target){
		
		this.legalHolidayTime = this.legalHolidayTime.addMinutes(target.legalHolidayTime.v());
		this.illegalHolidayTime = this.illegalHolidayTime.addMinutes(target.illegalHolidayTime.v());
		this.illegalSpecialHolidayTime = this.illegalSpecialHolidayTime.addMinutes(target.illegalSpecialHolidayTime.v());
	}
}
