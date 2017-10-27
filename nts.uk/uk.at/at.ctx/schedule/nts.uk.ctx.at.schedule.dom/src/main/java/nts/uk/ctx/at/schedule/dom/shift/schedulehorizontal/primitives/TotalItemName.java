package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author yennth
 *
 */
@StringMaxLength(10)
public class TotalItemName extends StringPrimitiveValue<PrimitiveValue<String>>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	public TotalItemName(String rawValue){
		super(rawValue);
	}
}
