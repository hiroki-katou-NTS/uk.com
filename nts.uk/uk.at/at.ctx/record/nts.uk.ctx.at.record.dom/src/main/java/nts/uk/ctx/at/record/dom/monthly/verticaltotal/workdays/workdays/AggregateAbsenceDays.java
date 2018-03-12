package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;

/**
 * 集計欠勤日数
 * @author shuichu_ishida
 */
@Getter
public class AggregateAbsenceDays {

	/** 欠勤枠NO */
	private int absenceFrameNo;
	/** 日数 */
	private AttendanceDaysMonth days;
	
	/**
	 * コンストラクタ
	 * @param absenceFrameNo 欠勤枠NO
	 */
	public AggregateAbsenceDays(int absenceFrameNo){
		
		this.absenceFrameNo = absenceFrameNo;
		this.days = new AttendanceDaysMonth(0.0);
	}

	/**
	 * ファクトリー
	 * @param absenceFrameNo 欠勤枠NO
	 * @param days 日数
	 * @return 集計欠勤日数
	 */
	public static AggregateAbsenceDays of(
			int absenceFrameNo,
			AttendanceDaysMonth days){
		
		val domain = new AggregateAbsenceDays(absenceFrameNo);
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
