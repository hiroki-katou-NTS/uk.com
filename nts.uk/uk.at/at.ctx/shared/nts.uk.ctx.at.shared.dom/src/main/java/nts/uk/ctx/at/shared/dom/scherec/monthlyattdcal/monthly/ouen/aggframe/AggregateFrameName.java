package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(12)
public class AggregateFrameName extends StringPrimitiveValue<AggregateFrameName> {
	
	/***/
	private static final long serialVersionUID = 1L;

	public AggregateFrameName(String rawValue) {
		super(rawValue);
	}

}
