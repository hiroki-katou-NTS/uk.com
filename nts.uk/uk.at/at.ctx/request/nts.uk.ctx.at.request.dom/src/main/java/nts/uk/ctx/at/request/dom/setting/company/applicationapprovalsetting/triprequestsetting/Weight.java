package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum Weight {
	// 未太字
	NOT_BOLDED(0),
	// 太字
	BOLD(1);
	public final int value;
}
