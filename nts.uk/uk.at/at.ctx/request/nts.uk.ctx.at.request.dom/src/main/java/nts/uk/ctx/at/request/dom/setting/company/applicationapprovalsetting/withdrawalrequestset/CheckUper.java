package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum CheckUper {
	// チェックしない
	DONT_CHECK(0),
	// チェックする（登録不可）
	CHECK(1);
	public final int value;
}
