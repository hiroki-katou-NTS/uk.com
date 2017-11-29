package nts.uk.ctx.at.record.dom.monthly.calc.flex;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;

/**
 * フレックス繰越時間
 * @author shuichi_ishida
 */
@Getter
public class FlexCarryforwardTime {

	/** フレックス繰越時間 */
	private AttendanceTimeMonth flexCarryforwardTime;
	/** フレックス繰越勤務時間 */
	private AttendanceTimeMonth flexCarryforwardWorkTime;
	/** フレックス繰越不足時間 */
	private AttendanceTimeMonth flexCarryforwardShortageTime;
	
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

		FlexCarryforwardTime domain = new FlexCarryforwardTime();
		domain.flexCarryforwardTime = flexCarryforwardTime;
		domain.flexCarryforwardWorkTime = flexCarryforwardWorkTime;
		domain.flexCarryforwardShortageTime = flexCarryforwardShortageTime;
		return domain;
	}
}
