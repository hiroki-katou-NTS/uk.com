package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.primitive;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * するしない区分
 * @author yennth
 *
 */
@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum ShowName {
	// 0:廃止しない
	NOT_SHOW(0),
	
	// 廃止する
	SHOW(1);

	public final int value;

}
