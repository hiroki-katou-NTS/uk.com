package nts.uk.ctx.bs.employee.dom.workplace.differinfor;

import lombok.AllArgsConstructor;
/**
 * 職場登録区分
 * @author yennth
 *
 */
@AllArgsConstructor
public enum RegWorkDiv {
	/** 区別する */
	NotDistinguish(0),
	/** 区別しない */
	Distinguish(1);
	public final int value;
}
