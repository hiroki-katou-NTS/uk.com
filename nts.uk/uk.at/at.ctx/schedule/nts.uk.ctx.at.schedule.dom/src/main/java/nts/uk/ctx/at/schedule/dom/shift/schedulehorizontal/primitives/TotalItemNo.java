package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
@ZeroPaddedCode
@IntegerRange(max = 99, min = 0)
public class TotalItemNo extends IntegerPrimitiveValue<PrimitiveValue<Integer>>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 集計項目NO **/
	public TotalItemNo(Integer rawValue){
		super(rawValue);
	}
}
