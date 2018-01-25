package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.primitive;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum MsgAdvance {
	// 0: 廃止しない
	NOT_ADVANCE(0),
	// 1: 廃止する
	ADVANCE(1);
	public final int value;
}
