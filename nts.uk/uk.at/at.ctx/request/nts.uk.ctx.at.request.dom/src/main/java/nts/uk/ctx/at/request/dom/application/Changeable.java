package nts.uk.ctx.at.request.dom.application;

import lombok.AllArgsConstructor;

/**
 * 勤種変更可否フラグ
 * @author yennth
 *
 */
@AllArgsConstructor
public enum Changeable {
	/** 変更不可 */
	CAN_NOT_CHANGE(0),
	/** 変更可能 */
	CHANGEABLE(1);
	public int value;
}
