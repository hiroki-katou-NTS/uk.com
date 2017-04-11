package nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.payday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

@IntegerMinValue(0)
@IntegerMaxValue(5)
public class SocialInsLevyMon extends IntegerPrimitiveValue<SocialInsLevyMon> {
	private static final long serialVersionUID = 1L;

	public SocialInsLevyMon(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
