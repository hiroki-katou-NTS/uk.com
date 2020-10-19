package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * @author ThanhNX
 *
 *         日別勤怠の何が変更されたか
 */
@Value
@AllArgsConstructor
public class ChangeDailyAttendance {

	/**
	 * 勤務情報
	 */
	public final boolean workInfo;

	/**
	 * 予定勤務情報
	 */
	public final boolean scheduleWorkInfo;

	/**
	 * 出退勤
	 */
	public final boolean attendance;

	/**
	 * 計算区分
	 */
	public final boolean calcCategory;

}
