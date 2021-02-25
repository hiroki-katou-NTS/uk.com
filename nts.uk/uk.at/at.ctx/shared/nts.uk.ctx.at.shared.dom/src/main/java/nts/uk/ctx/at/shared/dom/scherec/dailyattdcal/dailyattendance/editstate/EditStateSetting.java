package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 * 日別実績の編集状態 - enums
 *
 */
@AllArgsConstructor
public enum EditStateSetting {
	
	/** 手修正（本人） */
	HAND_CORRECTION_MYSELF(0),
	/** 手修正（他人） */
	HAND_CORRECTION_OTHER(1),
	/** 申請反映 */
	REFLECT_APPLICATION(2),
	/** 打刻反映 */
	IMPRINT(3),
	/** 申告反映 */
	DECLARE_APPLICATION(4);
	
	public final int value;
}
