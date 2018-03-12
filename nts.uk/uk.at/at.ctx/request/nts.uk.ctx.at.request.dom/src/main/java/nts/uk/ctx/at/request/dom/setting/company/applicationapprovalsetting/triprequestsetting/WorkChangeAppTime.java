package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum WorkChangeAppTime {
	// 表示しない
	NOT_DISPLAY(0),
	// 表示する
	DISPLAY(1);
	public final int value;
}
