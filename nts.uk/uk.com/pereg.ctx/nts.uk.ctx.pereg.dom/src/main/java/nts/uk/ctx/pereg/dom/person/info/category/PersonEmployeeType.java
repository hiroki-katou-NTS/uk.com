package nts.uk.ctx.pereg.dom.person.info.category;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 2, min = 1)
public enum PersonEmployeeType {
	// 1:個人(Person)
	PERSON(1), 
	// 2:社員(Employee)
	EMPLOYEE(2);

	public final int value;

}
