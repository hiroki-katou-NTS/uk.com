package nts.uk.ctx.at.function.dom.alarm.extractionrange.agreement;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = 1950, max = 9999)
public class YearOfAgreement extends IntegerPrimitiveValue<YearOfAgreement> {

	private static final long serialVersionUID = 1L;

	public YearOfAgreement(Integer rawValue) {
		super(rawValue);
		
	}

	

}