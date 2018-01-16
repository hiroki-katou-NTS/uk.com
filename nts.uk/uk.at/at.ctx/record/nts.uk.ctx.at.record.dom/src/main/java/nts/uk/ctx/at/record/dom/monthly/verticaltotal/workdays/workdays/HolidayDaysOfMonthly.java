package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;

/**
 * 月別実績の休日日数
 * @author shuichu_ishida
 */
@Getter
public class HolidayDaysOfMonthly {

	/** 日数 */
	private AttendanceDaysMonth days;
	
	/**
	 * コンストラクタ
	 */
	public HolidayDaysOfMonthly(){
		
		this.days = new AttendanceDaysMonth(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param days 日数
	 * @return 月別実績の休日日数
	 */
	public static HolidayDaysOfMonthly of(AttendanceDaysMonth days){
		
		val domain = new HolidayDaysOfMonthly();
		domain.days = days;
		return domain;
	}
}
