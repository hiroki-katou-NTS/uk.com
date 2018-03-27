package nts.uk.ctx.at.request.dom.application;

import lombok.AllArgsConstructor;

/**
 * 休憩反映区分
 * @author yennth
 *
 */
@AllArgsConstructor
public enum BreakReflect {
	/** シフト休憩に反映 */
	REFLEC_SHIFT_BREAK(0),
	/** 外出に反映 */
	REFLEC_GO_OUT(1);
	public int value;
	
}
