package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.leave;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;

/**
 * 集計休業日数
 * @author shuichu_ishida
 */
@Getter
public class AggregateLeaveDays {

	/** 休業区分 */
	private int leaveAtr;
	/** 日数 */
	private AttendanceDaysMonth days;
	
	/**
	 * コンストラクタ
	 * @param leaveAtr 休業区分
	 */
	public AggregateLeaveDays(int leaveAtr){
		
		this.leaveAtr = leaveAtr;
		this.days = new AttendanceDaysMonth(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param leaveAtr 休業区分
	 * @param days 日数
	 * @return 集計休業日数
	 */
	public static AggregateLeaveDays of(
			int leaveAtr,
			AttendanceDaysMonth days){
		
		val domain = new AggregateLeaveDays(leaveAtr);
		domain.days = days;
		return domain;
	}
	
	/**
	 * 日数を加算する
	 * @param days 日数
	 */
	public void addDays(Double days){
		this.days = this.days.addDays(days);
	}
}
