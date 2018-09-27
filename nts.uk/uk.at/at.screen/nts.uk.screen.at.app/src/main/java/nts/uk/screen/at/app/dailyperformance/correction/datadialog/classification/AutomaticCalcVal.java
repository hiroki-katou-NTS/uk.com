package nts.uk.screen.at.app.dailyperformance.correction.datadialog.classification;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
import nts.arc.primitive.constraint.StringRegEx;

@IntegerRange(max = 2, min = 0)
@StringRegEx("[0,2]$")
public class AutomaticCalcVal extends IntegerPrimitiveValue<AutomaticCalcVal>{

	private static final long serialVersionUID = 1L;
	public AutomaticCalcVal(Integer rawValue) {
		super(rawValue);
	}

}
