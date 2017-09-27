package nts.uk.ctx.bs.person.dom.person.info.setting.user;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum RecentRegType {
	//表示しない (Hide)
	HIDE(0),
	//表示する (Display)
	DISPLAY(1);
	public final int value;
}
