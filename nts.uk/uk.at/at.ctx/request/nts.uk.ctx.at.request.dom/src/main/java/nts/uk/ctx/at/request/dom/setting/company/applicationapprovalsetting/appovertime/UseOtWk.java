package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum UseOtWk {
	NOT_USE(0),
	USE(1);
	public final int value;
}
