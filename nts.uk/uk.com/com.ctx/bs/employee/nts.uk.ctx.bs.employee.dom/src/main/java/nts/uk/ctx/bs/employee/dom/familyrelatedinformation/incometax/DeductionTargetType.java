package nts.uk.ctx.bs.employee.dom.familyrelatedinformation.incometax;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 3, min = 1)
public enum DeductionTargetType {

	/** NotTarget */
	NOT_TARGET(1),

	/** Spouse - */
	SPOUSE(2),

	/** OldSpouse - */
	OLD_SPOUSE(3);

	public final int value;

}
