package nts.uk.ctx.at.shared.dom.category.primitives;

import nts.arc.primitive.PrimitiveValue;
import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author yennth
 *
 */
@StringMaxLength(500)
public class Memo extends StringPrimitiveValue<PrimitiveValue<String>>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	public Memo(String rawValue){
		super(rawValue);
	}
}
