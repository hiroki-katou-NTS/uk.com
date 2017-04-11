package nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.standardday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

@IntegerMinValue(1)
@IntegerMaxValue(31)
public class SocialInsStdDay extends IntegerPrimitiveValue<SocialInsStdDay> {

	private static final long serialVersionUID = 1L;

	public SocialInsStdDay(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
