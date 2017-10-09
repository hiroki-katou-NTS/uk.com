package nts.uk.ctx.bs.person.dom.person.setting.selectionitem.selection;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum InitSelection {
	NOT_INIT_SELECTION(0), // 初期選択としない
	INIT_SELECTION(1);// 初期選択とする

	public final int value;
}
