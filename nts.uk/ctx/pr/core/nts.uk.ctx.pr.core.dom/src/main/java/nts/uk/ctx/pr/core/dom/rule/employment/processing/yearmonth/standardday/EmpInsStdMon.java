package nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.standardday;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

@IntegerMinValue(1)
@IntegerMaxValue(12)
public class EmpInsStdMon extends IntegerPrimitiveValue<EmpInsStdMon> {

	private static final long serialVersionUID = 1L;

	public EmpInsStdMon(Integer rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
