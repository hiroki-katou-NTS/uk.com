package nts.uk.ctx.at.schedule.dom.budget.premium;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(500)
public class PersonCostMemo extends StringPrimitiveValue<PersonCostMemo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4081433361798694697L;

	public PersonCostMemo(String rawValue) {
		super(rawValue);
	}

}
