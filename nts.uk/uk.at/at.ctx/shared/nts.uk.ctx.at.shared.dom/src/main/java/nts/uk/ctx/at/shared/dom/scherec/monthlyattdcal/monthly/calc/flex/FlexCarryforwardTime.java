package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.flex;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonthWithMinus;

/**
 * フレックス繰越時間
 * @author shuichi_ishida
 */
@Getter
public class FlexCarryforwardTime implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** フレックス繰越時間 */
	@Setter
	private AttendanceTimeMonthWithMinus flexCarryforwardTime;
	/** フレックス繰越勤務時間 */
	@Setter
	private AttendanceTimeMonth flexCarryforwardWorkTime;
	/** フレックス繰越不足時間 */
	@Setter
	private AttendanceTimeMonth flexCarryforwardShortageTime;
	/** フレックス繰越不可時間 */
	@Setter
	private AttendanceTimeMonth flexNotCarryforwardTime;
	
	/**
	 * コンストラクタ
	 */
	public FlexCarryforwardTime(){
		
		this.flexCarryforwardTime = new AttendanceTimeMonthWithMinus(0);
		this.flexCarryforwardWorkTime = new AttendanceTimeMonth(0);
		this.flexCarryforwardShortageTime = new AttendanceTimeMonth(0);
		this.flexNotCarryforwardTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param flexCarryforwardTime フレックス繰越時間
	 * @param flexCarryforwardWorkTime フレックス繰越勤務時間
	 * @param flexCarryforwardShortageTime フレックス繰越不足時間
	 * @param flexNotCarryforwardTime フレックス繰越不可時間
	 * @return フレックス繰越時間
	 */
	public static FlexCarryforwardTime of(
			AttendanceTimeMonthWithMinus flexCarryforwardTime,
			AttendanceTimeMonth flexCarryforwardWorkTime,
			AttendanceTimeMonth flexCarryforwardShortageTime,
			AttendanceTimeMonth flexNotCarryforwardTime){

		val domain = new FlexCarryforwardTime();
		domain.flexCarryforwardTime = flexCarryforwardTime;
		domain.flexCarryforwardWorkTime = flexCarryforwardWorkTime;
		domain.flexCarryforwardShortageTime = flexCarryforwardShortageTime;
		domain.flexNotCarryforwardTime = flexNotCarryforwardTime;
		return domain;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(FlexCarryforwardTime target){
		
		this.flexCarryforwardTime = this.flexCarryforwardTime.addMinutes(target.flexCarryforwardTime.v());
		this.flexCarryforwardWorkTime = this.flexCarryforwardWorkTime.addMinutes(
				target.flexCarryforwardWorkTime.v());
		this.flexCarryforwardShortageTime = this.flexCarryforwardShortageTime.addMinutes(
				target.flexCarryforwardShortageTime.v());
		this.flexNotCarryforwardTime = this.flexNotCarryforwardTime.addMinutes(
				target.flexNotCarryforwardTime.v());
	}
}
