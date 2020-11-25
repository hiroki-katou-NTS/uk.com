package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.workdays.specificdays;

import java.io.Serializable;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.SpecificDateItemNo;

/**
 * 集計特定日数
 * @author shuichi_ishida
 */
@Getter
public class AggregateSpecificDays implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 特定日項目No */
	private SpecificDateItemNo specificDayItemNo;
	
	/** 特定日数 */
	private AttendanceDaysMonth specificDays;
	/** 休出特定日数 */
	private AttendanceDaysMonth holidayWorkSpecificDays;
	
	/**
	 * コンストラクタ
	 * @param specificDayItemNo 特定日項目No
	 */
	public AggregateSpecificDays(SpecificDateItemNo specificDayItemNo){
		
		this.specificDayItemNo = specificDayItemNo;
		this.specificDays = new AttendanceDaysMonth(0.0);
		this.holidayWorkSpecificDays = new AttendanceDaysMonth(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param specificDayItemNo 特定日項目No
	 * @param specificDays 特定日数
	 * @param holidayWorkSpecificDays 休出特定日数
	 * @return 集計特定日数
	 */
	public static AggregateSpecificDays of(
			SpecificDateItemNo specificDayItemNo,
			AttendanceDaysMonth specificDays,
			AttendanceDaysMonth holidayWorkSpecificDays){
		
		val domain = new AggregateSpecificDays(specificDayItemNo);
		domain.specificDays = specificDays;
		domain.holidayWorkSpecificDays = holidayWorkSpecificDays;
		return domain;
	}
	
	/**
	 * 特定日数に日数を加算する
	 * @param days 日数
	 */
	public void addDaysToSpecificDays(Double days){
		this.specificDays = this.specificDays.addDays(days);
	}
	
	/**
	 * 休出特定日数に日数を加算する
	 * @param days 日数
	 */
	public void addDaysToHolidayWorkSpecificDays(Double days){
		this.holidayWorkSpecificDays = this.holidayWorkSpecificDays.addDays(days);
	}
}
