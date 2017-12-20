package nts.uk.ctx.pereg.dom.person.personinfoctgdata.item;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 5, min = 1)

public enum DataStateType {

	// 文字列 (String)
	String(1),
	// 数値 (Numeric)
	Numeric(2),
	// 日付 (Date)
	Date(3);

	public final int value;

}
