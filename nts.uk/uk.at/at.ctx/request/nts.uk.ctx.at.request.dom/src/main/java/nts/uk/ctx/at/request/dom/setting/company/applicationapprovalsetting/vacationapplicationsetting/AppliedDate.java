package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 2, min =0)
public enum AppliedDate {
	// チェックしない
	DONT_CHECK(0),
	// チェックする（登録可）
	CHECK_AVAILABLE(1),
	// チェックする（登録不可）
	CHECK_IMPOSSIBLE(2);
	public final int value;
}
