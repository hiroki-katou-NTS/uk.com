package nts.uk.ctx.at.record.dom.monthly.calc.flex;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * フレックス不足控除時間
 * @author shuichu_ishida
 */
@Getter
@Setter
public class FlexShortDeductTime {

	/** 年休控除日数 */
	private AttendanceDaysMonth annualLeaveDeductDays;
	/** 欠勤控除時間 */
	private AttendanceTimeMonth absenceDeductTime;
	/** 控除前のフレックス不足時間 */
	private AttendanceTimeMonth flexShortTimeBeforeDeduct;
	/** 余分な控除時間のエラーフラグ */
	private boolean errorAtrOfExtraDeductTime;
	
	/**
	 * コンストラクタ
	 */
	public FlexShortDeductTime(){
		
		this.annualLeaveDeductDays = new AttendanceDaysMonth(0.0);
		this.absenceDeductTime = new AttendanceTimeMonth(0);
		this.flexShortTimeBeforeDeduct = new AttendanceTimeMonth(0);
		this.errorAtrOfExtraDeductTime = false;
	}
	
	/**
	 * ファクトリー
	 * @param annualLeaveDeductDays 年休控除日数
	 * @param absenceDeductTime 欠勤控除時間
	 * @param flexShortTimeBeforeDeduct 控除前のフレックス不足時間
	 * @return フレックス不足控除時間
	 */
	public static FlexShortDeductTime of(
			AttendanceDaysMonth annualLeaveDeductDays,
			AttendanceTimeMonth absenceDeductTime,
			AttendanceTimeMonth flexShortTimeBeforeDeduct){
		
		FlexShortDeductTime domain = new FlexShortDeductTime();
		domain.annualLeaveDeductDays = annualLeaveDeductDays;
		domain.absenceDeductTime = absenceDeductTime;
		domain.flexShortTimeBeforeDeduct = flexShortTimeBeforeDeduct;
		return domain;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(FlexShortDeductTime target){
		
		this.flexShortTimeBeforeDeduct = this.flexShortTimeBeforeDeduct.addMinutes(target.flexShortTimeBeforeDeduct.v());
	}
}
