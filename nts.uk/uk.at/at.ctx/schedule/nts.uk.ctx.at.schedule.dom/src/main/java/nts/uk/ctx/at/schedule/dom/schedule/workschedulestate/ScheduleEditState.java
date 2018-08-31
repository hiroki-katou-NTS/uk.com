package nts.uk.ctx.at.schedule.dom.schedule.workschedulestate;

import lombok.AllArgsConstructor;

/**
 * 予定編集状態
 * 
 * @author sonnh1
 *
 */
@AllArgsConstructor
public enum ScheduleEditState {
	/**
	 *  手修正(本人)
	 */
	HAND_CORRECTION_PRINCIPAL(1),
	/**
	 *  手修正(他人)
	 */
	HAND_CORRECTION_ORDER(2),
	/**
	 *  申請反映
	 */
	REFLECT_APPLICATION(3);

	public final int value;
}
