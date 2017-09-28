package nts.uk.ctx.at.schedule.dom.shift.schedulehorizontal.primitives;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author yennth
 *
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
public class CategoryCode extends StringPrimitiveValue<PrimitiveValue<String>>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	public CategoryCode(String rawValue){
		super(rawValue);
	}
}
