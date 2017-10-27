package nts.uk.ctx.at.record.dom.calculationattribute.enums;

import lombok.AllArgsConstructor;

/**
 * 
 * @author nampt
 * 時間外の上限設定
 *
 */
@AllArgsConstructor
public enum LimitOfOverTimeSetting {
	
	/* 上限なし */
	NO_UPPER_LIMIT(0),
	/* 事前申請を上限とする */
	PRE_APPLY_TOBE_UPPER_LIMIT(1),
	/* 指示時間を上限とする */
	INDICATED_TIME_TOBE_UPPER_LIMIT(2);
	
	public final int value;
}
