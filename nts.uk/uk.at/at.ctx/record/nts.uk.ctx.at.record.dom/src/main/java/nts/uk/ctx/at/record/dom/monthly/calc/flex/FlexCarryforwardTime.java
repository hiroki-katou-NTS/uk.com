package nts.uk.ctx.at.record.dom.monthly.calc.flex;

import lombok.Getter;
import lombok.Setter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * フレックス繰越時間
 * @author shuichi_ishida
 */
@Getter
public class FlexCarryforwardTime {

	/** フレックス繰越時間 */
	@Setter
	private AttendanceTimeMonth flexCarryforwardTime;
	/** フレックス繰越勤務時間 */
	@Setter
	private AttendanceTimeMonth flexCarryforwardWorkTime;
	/** フレックス繰越不足時間 */
	@Setter
	private AttendanceTimeMonth flexCarryforwardShortageTime;
	
	/**
	 * コンストラクタ
	 */
	public FlexCarryforwardTime(){
		
		this.flexCarryforwardTime = new AttendanceTimeMonth(0);
		this.flexCarryforwardWorkTime = new AttendanceTimeMonth(0);
		this.flexCarryforwardShortageTime = new AttendanceTimeMonth(0);
	}
	
	/**
	 * ファクトリー
	 * @param flexCarryforwardTime フレックス繰越時間
	 * @param flexCarryforwardWorkTime フレックス繰越勤務時間
	 * @param flexCarryforwardShortageTime フレックス繰越不足時間
	 * @return
	 */
	public static FlexCarryforwardTime of(
			AttendanceTimeMonth flexCarryforwardTime,
			AttendanceTimeMonth flexCarryforwardWorkTime,
			AttendanceTimeMonth flexCarryforwardShortageTime){

		val domain = new FlexCarryforwardTime();
		domain.flexCarryforwardTime = flexCarryforwardTime;
		domain.flexCarryforwardWorkTime = flexCarryforwardWorkTime;
		domain.flexCarryforwardShortageTime = flexCarryforwardShortageTime;
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
	}
}
