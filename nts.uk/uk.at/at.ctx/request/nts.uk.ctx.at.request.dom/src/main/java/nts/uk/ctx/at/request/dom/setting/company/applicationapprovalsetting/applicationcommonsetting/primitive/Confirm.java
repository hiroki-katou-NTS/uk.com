package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.primitive;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 反映するしない区分
 * @author yennth
 *
 */
@AllArgsConstructor
@IntegerRange(max = 2, min = 0)
public enum Confirm {
	// 0-反映しない(強制反映不可)
	NOT_REFLECT_NOT_POSSIBLE(0),
	// 1-反映しない(強制反映可)
	NOT_REFLECT_ENABLE(1),
	// 2-反映する
	REFLECT(2);
	public final int value;
}
