package nts.uk.ctx.pereg.dom.person.info.dateitem;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 6, min = 1)
public enum DateType {

	// 1: 年月日 (YearMonthDay)
	YEARMONTHDAY(1),

	// 2: 年月 (YearMonth)
	YEARMONTH(2),

	// 3: 年 (Year)
	YEAR(3);

	public final int value;
}