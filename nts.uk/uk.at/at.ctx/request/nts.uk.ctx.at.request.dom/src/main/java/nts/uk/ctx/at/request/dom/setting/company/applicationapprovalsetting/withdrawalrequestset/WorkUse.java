package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 2, min =0)
public enum WorkUse {
	// 利用しない
	NOT_USE(0),
	// 利用する
	USE(1),
	// 半休のみ利用する
	USE_ONLY_HALF_HD(2);
	public final int value;
}
