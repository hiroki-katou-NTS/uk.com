package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.grantdayperrelationship;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(max=366, min=0)
public class GrantedDay extends IntegerPrimitiveValue<GrantedDay> {

	
	private static final long serialVersionUID = 1L;

	public GrantedDay(Integer value) {
		super(value);
	}

}
