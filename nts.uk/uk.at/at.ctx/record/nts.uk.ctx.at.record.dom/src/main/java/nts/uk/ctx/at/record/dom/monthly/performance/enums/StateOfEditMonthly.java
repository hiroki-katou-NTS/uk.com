package nts.uk.ctx.at.record.dom.monthly.performance.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 * 月別実績の編集状態 - enum
 */
@AllArgsConstructor
public enum StateOfEditMonthly {

	/** 手修正（本人） */
	HAND_CORRECTION_MYSELF(0),
	/** 手修正（他人） */
	HAND_CORRECTION_OTHER(1);
	
	public final int value;
}
