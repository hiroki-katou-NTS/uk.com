package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ThanhNX
 *
 *         日別勤怠の何が変更されたか
 */
@Data
@AllArgsConstructor
public class ChangeDailyAttendance {

	/** 勤務情報 */
	public boolean workInfo;

	/** 予定勤務情報 */
	public boolean scheduleWorkInfo;

	/** 出退勤 */
	public boolean attendance;

	/** 計算区分 */
	public boolean calcCategory;
	
	/** 固定休憩補正 */
	public boolean fixBreakCorrect;

}
