package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;

/**
 * 当月フレックス時間
 * @author shuichi_ishida
 */
@Getter
public class FlexTimeCurrentMonth implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** フレックス時間 */
	@Setter
	private AttendanceTimeMonthWithMinus flexTime;
	/** 基準時間 */
	@Setter
	private AttendanceTimeMonth standardTime;
	/** 週平均超過時間 */
	@Setter
	private AttendanceTimeMonth excessWeekAveTime;
	
	/**
	 * コンストラクタ
	 */
	public FlexTimeCurrentMonth(){
		this.flexTime = new AttendanceTimeMonthWithMinus(0);
		this.standardTime = new AttendanceTimeMonth(0);
		this.excessWeekAveTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param flexTime フレックス時間
	 * @param standardTime 基準時間
	 * @param excessWeekAveTime 週平均超過時間
	 * @return 当月フレックス時間
	 */
	public static FlexTimeCurrentMonth of(
			AttendanceTimeMonthWithMinus flexTime,
			AttendanceTimeMonth standardTime,
			AttendanceTimeMonth excessWeekAveTime){
		
		val domain = new FlexTimeCurrentMonth();
		domain.flexTime = flexTime;
		domain.standardTime = standardTime;
		domain.excessWeekAveTime = excessWeekAveTime;
		return domain;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(FlexTimeCurrentMonth target){
		
		this.flexTime = this.flexTime.addMinutes(target.flexTime.v());
		this.standardTime = this.standardTime.addMinutes(target.standardTime.v());
		this.excessWeekAveTime = this.excessWeekAveTime.addMinutes(target.excessWeekAveTime.v());
	}
}
