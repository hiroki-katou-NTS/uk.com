package nts.uk.ctx.bs.employee.dom.leaveholiday;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 5, min = 1)
public enum LeaveHolidayType {
	
	/**
	 * 休業休職区分  Leave of absence classification
	 */

	// 1:休職
	LEAVEOFABSENCE(1),

	// 2: 介護休業
	NURSINGCARELEAVE(2),

	// 3: 育児介護
	CHILDCARENURSING(3),

	// 4: 産前休業
	MIDWEEKCLOSURE(4),

	// 5: 産後休業
	AFTERCHILDBIRTH(5);

	public final int value;

}
