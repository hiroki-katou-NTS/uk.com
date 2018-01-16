package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.workdays;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.record.dom.monthly.AttendanceDaysMonth;

/**
 * 月別実績の欠勤日数
 * @author shuichu_ishida
 */
@Getter
public class AbsenceDaysOfMonthly {

	/** 欠勤合計日数 */
	private AttendanceDaysMonth totalAbsenceDays;
	/** 欠勤日数 */
	private List<AggregateAbsenceDays> absenceDaysList;
	
	/**
	 * コンストラクタ
	 */
	public AbsenceDaysOfMonthly(){
		
		this.totalAbsenceDays = new AttendanceDaysMonth(0.0);
		this.absenceDaysList = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param totalAbsenceDays 欠勤合計日数
	 * @param absenceDaysList 欠勤日数
	 * @return 月別実績の欠勤日数
	 */
	public static AbsenceDaysOfMonthly of(
			AttendanceDaysMonth totalAbsenceDays,
			List<AggregateAbsenceDays> absenceDaysList){
		
		val domain = new AbsenceDaysOfMonthly();
		domain.totalAbsenceDays = totalAbsenceDays;
		domain.absenceDaysList = absenceDaysList;
		return domain;
	}
}
