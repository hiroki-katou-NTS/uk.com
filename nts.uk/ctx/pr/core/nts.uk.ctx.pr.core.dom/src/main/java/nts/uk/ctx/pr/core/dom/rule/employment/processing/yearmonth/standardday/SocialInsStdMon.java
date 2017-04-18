package nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.standardday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

@IntegerRange(min = -1, max = 12)
public class SocialInsStdMon extends IntegerPrimitiveValue<SocialInsStdMon> {

	private static final long serialVersionUID = 1L;

	public SocialInsStdMon(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
