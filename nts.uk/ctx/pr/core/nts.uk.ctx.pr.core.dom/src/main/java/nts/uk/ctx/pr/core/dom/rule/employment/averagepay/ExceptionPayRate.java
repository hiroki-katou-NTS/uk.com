package nts.uk.ctx.pr.core.dom.rule.employment.averagepay;


import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 * 例外時割合
 * Exception Pay Rate
 * @author Doan Duy Hung
 *
 */
@IntegerMaxValue(100)
@IntegerMinValue(0)
public class ExceptionPayRate extends IntegerPrimitiveValue<ExceptionPayRate>{
	
	public ExceptionPayRate(int value) {		
		//super(Integer.parseInt(value));
		super(value);
		// TODO Auto-generated constructor stub
	}
}
