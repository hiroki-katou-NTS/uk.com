package nts.uk.ctx.bs.company.dom.company.primitive;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 
 * @author yennth
 *
 */
@StringMaxLength(10)
public class ShortComName extends StringPrimitiveValue<ShortComName>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	/** 会社略名 **/
	public ShortComName(String rawValue){
		super(rawValue);
	}
}
