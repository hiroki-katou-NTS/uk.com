package nts.uk.ctx.at.record.dom.monthly.verticaltotal.workdays.paydays;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.PayItemCountAtr;
import nts.uk.ctx.at.record.dom.monthly.vtotalmethod.PayItemCountOfMonthly;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 月別実績の給与用日数
 * @author shuichu_ishida
 */
@Getter
public class PayDaysOfMonthly {

	/** 給与出勤日数 */
	private AttendanceDaysMonth payAttendanceDays;
	/** 給与欠勤日数 */
	private AttendanceDaysMonth payAbsenceDays;
	
	/**
	 * コンストラクタ
	 */
	public PayDaysOfMonthly(){
		
		this.payAttendanceDays = new AttendanceDaysMonth(0.0);
		this.payAbsenceDays = new AttendanceDaysMonth(0.0);
	}
	
	/**
	 * ファクトリー
	 * @param payAttendanceDays 給与出勤日数
	 * @param payAbsenceDays　給与欠勤日数
	 * @return 月別実績の給与用日数
	 */
	public static PayDaysOfMonthly of(
			AttendanceDaysMonth payAttendanceDays,
			AttendanceDaysMonth payAbsenceDays){
		
		val domain = new PayDaysOfMonthly();
		domain.payAttendanceDays = payAttendanceDays;
		domain.payAbsenceDays = payAbsenceDays;
		return domain;
	}
	
	/**
	 * 集計
	 * @param workType 勤務種類
	 * @param payItemCount 月別実績の給与項目カウント
	 */
	public void aggregate(WorkType workType, PayItemCountOfMonthly payItemCount){

		if (workType == null) return;
		
		// 勤務種類コードを取得
		val workTypeCode = workType.getWorkTypeCode();
		
		// 給与出勤日数を加算する
		this.payAttendanceDays = this.payAttendanceDays.addDays(
				payItemCount.getDays(PayItemCountAtr.PAY_ATTENDANCE_DAYS, workTypeCode));
		
		// 給与欠勤日数を加算する
		this.payAbsenceDays = this.payAbsenceDays.addDays(
				payItemCount.getDays(PayItemCountAtr.PAY_ABSENCE_DAYS, workTypeCode));
	}

	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(PayDaysOfMonthly target){
		
		this.payAttendanceDays = this.payAttendanceDays.addDays(target.payAttendanceDays.v());
		this.payAbsenceDays = this.payAbsenceDays.addDays(target.payAbsenceDays.v());
	}
}
